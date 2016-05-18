package dominion.model;

import dominion.model.database.*;
import dominion.model.info.*;

import java.sql.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Game {

    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Constructor, create, load and member declarations" defaultstate="collapsed">
    private final int id;
    private final String cardSet;
    private final HashMap<Integer, GameCard> cards;
    private final HashMap<Integer, Player> players;
    private int player;
    private String state;
    private int winner;

    private Game(int id, String cardSet) {
        this(id, cardSet, -1, null, -1);
    }

    private Game(int id, String cardSet, int player, String state, int winner) {
        this.id = id;
        this.cardSet = cardSet;
        this.state = state;
        this.winner = winner;
        this.player = player;
        this.cards = new HashMap<>();
        this.players = new HashMap<>();
    }

    public static Game create(Connection con, String[] playerNames, String cardSet) throws Exception {
        CardSet set = CardSet.parse(cardSet, CardSet.Random);
        Game game = Game.insert(con, set.getName());
        game.state = "Action";
        game.addCard(GameCard.create(con, game, "Treasure", CardInfo.Gold, playerNames.length));
        game.addCard(GameCard.create(con, game, "Treasure", CardInfo.Silver, playerNames.length));
        GameCard copper = game.addCard(GameCard.create(con, game, "Treasure", CardInfo.Copper, playerNames.length));
        game.addCard(GameCard.create(con, game, "Victory", CardInfo.Province, playerNames.length));
        game.addCard(GameCard.create(con, game, "Victory", CardInfo.Duchy, playerNames.length));
        GameCard estate = game.addCard(GameCard.create(con, game, "Victory", CardInfo.Estate, playerNames.length));
        game.addCard(GameCard.create(con, game, "Victory", CardInfo.Curse, playerNames.length));
        CardInfo[] cards = set.getCards();
        for (CardInfo cardInfo : cards) {
            game.addCard(GameCard.create(con, game, "Kingdom", cardInfo, playerNames.length));
        }
        // Could make first player random...
        for (int i = 0; i < playerNames.length; i++) {
            Player newPlayer = game.addPlayer(Player.create(con, game, playerNames[i], estate, copper));
            if (i == 0) {
                game.updatePlayer(con, newPlayer.getId());
            }
        }
        return game;
    }

    private static Game insert(Connection con, String cardSet) throws Exception {
        String sql = "INSERT INTO games (cardset, state) VALUES (?, \"Action\")";
        Object[] args = new Object[]{cardSet};
        int id = Database.executeInsert(con, sql, args);
        return new Game(id, cardSet);
    }

    public static Game load(Connection con, int id) throws Exception {
        return Game.loadCards(Game.loadPlayers(con, id), con);
    }

    private static Game load(ResultSet rs) throws Exception {
        return new Game(rs.getInt("games.id"), rs.getString("games.cardSet"), rs.getInt("games.player"), rs.getString("games.state"), rs.getInt("games.winner"));
    }

    private static Game loadCards(Game game, Connection con)throws Exception {
        String sql = "SELECT * FROM gamecards INNER JOIN games ON game = games.id WHERE games.id = ? ORDER BY deck";
        Object[] args = new Object[]{game.getId()};
        try (PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = Database.executeQuery(con, stmt, args)) {
            while (rs.next()) {
                game.addCard(GameCard.load(game, rs));
            }
        }
        return game;
    }
    
    private static Game loadPlayers(Connection con, int id) throws Exception {
        String sql = "SELECT * FROM playercards"
                + " INNER JOIN players ON playercards.player = players.id"
                + " INNER JOIN games ON players.game = games.id"
                + " INNER JOIN gamecards ON playercards.card = gamecards.id"
                + " WHERE games.id = ?"
                + " ORDER BY players.id, playercards.`order`, playercards.pile";
        Object[] args = new Object[]{id};
        Game game = null;
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = Database.executeQuery(con, stmt, args)) {
            int min = 0, max = 0;
            while (rs.next()) {
                if (game == null) {
                    game = Game.load(rs);
                }
                int playerId = rs.getInt("players.id");
                Player player;
                if (!game.players.containsKey(playerId)) {
                    player = game.addPlayer(Player.load(game, rs));
                } else {
                    player = game.players.get(playerId);
                }
                PlayerCard playerCard = player.addCard(PlayerCard.load(player, rs));
                Order order = player.getOrder();
                if(playerCard.getOrder() > order.getMax()) order.setMax(max);
                else if(playerCard.getOrder() < order.getMin()) order.setMin(min);
            }
        }
        return game;
    }
    
    // </editor-fold>
    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Public methods and accessors" defaultstate="collapsed">
    public void betCoins(Connection con, int[] playerCardId) throws Exception {
        // Guard.validateCurrentPlayer(this, "betCoins");
        Guard.validateState(this, "Bet");
        // Validates it are are coin cards at the same time...
        Player currentPlayer = this.getCurrentPlayer();
        int total = 0;
        for (int i = 0; i < playerCardId.length; i++) {
            total += getGameCardValueByPlayerCardId(currentPlayer, playerCardId[i]);
        }
        // Verified, update the cards and the player his coin value...
        for (int i = 0; i < playerCardId.length; i++) {
            PlayerCard playerCard = currentPlayer.getCard(playerCardId[i]);
            // Move from hand to table
            playerCard.updatePileAndOrder(con, "Table", currentPlayer.getOrder().newMax());
        }
        currentPlayer.updateCoins(con, currentPlayer.getCoins() + total);
    }

    public PlayerCard buyCard(Connection con, int gameCardId) throws Exception {
        // Guard.validateCurrentPlayer(this, "buyCard");
        Guard.validateState(this, "Buy");
        // Get card, validate card...
        GameCard gameCard = this.getCard(gameCardId);
        Guard.validateNotNull(gameCard, "Card not in game. - Game.buyCard");
        Guard.validateTrue(gameCard.getCount() > 0, "No more cards available. - Game.buyCard");
        // Get current player, validate player...
        Player currentPlayer = this.getCurrentPlayer();
        Guard.validateTrue(currentPlayer.getCoins() >= gameCard.getCost(), "Current player is too poor. - Game.buyCard");
        // Update...
        gameCard.updateCount(con, gameCard.getCount() - 1);
        currentPlayer.updateCoins(con, currentPlayer.getCoins() - gameCard.getCost());
        return currentPlayer.addCard(PlayerCard.create(con, currentPlayer, gameCardId, "Discard", currentPlayer.getOrder().newMin()));
    }

    public void cancelActions(Connection con) throws Exception {
        // Guard.validateCurrentPlayer(this, "cancelActions");
        Guard.validateState(this, "Action");
        this.updateState(con, "Bet");
    }

    public void cancelBet(Connection con) throws Exception {
        // Guard.validateCurrentPlayer(this, "cancelBet");
        Guard.validateState(this, "Bet");
        this.updateState(con, "Buy");
    }

    public void cancelBuy(Connection con) throws Exception {
        // Je moet je speelgebied opruimen
        // ! leg alle handkaarten en kaarten uit je speelgebied op je eigen aflegstapel
        // ! neem terug 5 kaarten uit je trekstapel in de hand
        // (indien minder dan 5 beschikbaar, eerst rest nemen, dan pas aanvullen met geschudde aflegstapel)
        // Guard.validateCurrentPlayer(this, "cancelBuy");
        Guard.validateState(this, "Buy");
        Player current = this.getCurrentPlayer();
        current.discardCards(con, c -> !c.getPile().equals("Deck"));

        Player next = this.getNextPlayer();
        next.updateAll(con, 1, 1, 0);
        this.updatePlayerAndState(con, next.getId(), "Action");
    }

    public GameCard getCard(int id) {
        return this.cards.get(id);
    }

    public GameCard getCard(PlayerCard playerCard) {
        return this.cards.get(playerCard.getCardId());
    }

    public GameCard[] getCards() {
        return cards.values().toArray(new GameCard[cards.size()]);
    }

    public GameCard[] getCards(Predicate<? super GameCard> filter) {
        return this.cards.values().stream().filter(filter).toArray(size -> new GameCard[size]);
    }

    public String getCardSet() {
        return this.cardSet;
    }

    public Player getCurrentPlayer() {
        return this.players.get(this.player);
    }

    public int getCurrentPlayerId() {
        return this.player;
    }

    public int getId() {
        return this.id;
    }

    private Player getNextPlayer() {
        List<Player> sorted = this.players.values().stream().sorted((p1, p2) -> p1.getId() - p2.getId()).collect(Collectors.toList());
        int index = sorted.indexOf(this.getCurrentPlayer());
        index += 1;
        if (index >= sorted.size()) {
            index = 0;
        }
        return sorted.get(index);
    }

    public Player getPlayer(int id) {
        return this.players.get(id);
    }

    public Player getPlayer(Predicate<? super Player> filter) {
        Optional<Player> result = this.players.values().stream().filter(filter).findFirst();
        return result.isPresent() ? result.get() : null;
    }

    public Player[] getPlayers() {
        return this.players.values().toArray(new Player[this.players.size()]);
    }

    public Player[] getPlayers(Predicate<? super Player> filter) {
        return this.players.values().stream().filter(filter).toArray(size -> new Player[size]);
    }

    public String getState() {
        return this.state;
    }

    // </editor-fold>
    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Private methods" defaultstate="collapsed">
    private GameCard addCard(GameCard value) {
        Guard.validateNull(this.cards.put(value.getId(), value), "Card already in game. - Game.addCard");
        return value;
    }

    private Player addPlayer(Player value) {
        Guard.validateNull(this.players.put(value.getId(), value), "Player already in game. - Game.addPlayer");
        return value;
    }

    private int getGameCardValueByPlayerCardId(Player currentPlayer, int playerCardId) {
        PlayerCard playerCard = currentPlayer.getCard(playerCardId);
        Guard.validateNotNull(playerCard, "Card does not belong to the player or does not exist. - Player2.getCoinCardValueById");
        Guard.validateTrue(playerCard.getPile().equals("Hand"), "Card is not in the player his hand. - Player2.getCoinCardValueByCardId");
        GameCard gameCard = this.getCard(playerCard);
        Guard.validateTrue(gameCard.getIsCoin(), "Card is not a coin. - Player2.getCoinCardValueByCardId");
        return gameCard.getValue();
    }

    private void updatePlayerAndState(Connection con, int playerId, String state) throws Exception {
        String sql = "UPDATE games SET player = ?, state = ? WHERE id = ?";
        Object[] args = new Object[]{playerId, state, this.id};
        int affected = Database.executeUpdate(con, sql, args);
        Guard.validateAffected(1, affected, "Game.updatePlayerAndState");
        this.player = playerId;
        this.state = state;
    }

    private void updatePlayer(Connection con, int playerId) throws Exception {
        String sql = "UPDATE games SET player = ? WHERE id = ?";
        Object[] args = new Object[]{playerId, this.id};
        int affected = Database.executeUpdate(con, sql, args);
        Guard.validateAffected(1, affected, "Game.updatePlayer");
        this.player = playerId;
    }

    private void updateState(Connection con, String state) throws Exception {
        String sql = "UPDATE games SET state = ? WHERE id = ?";
        Object[] args = new Object[]{state, this.id};
        int affected = Database.executeUpdate(con, sql, args);
        Guard.validateAffected(1, affected, "Game.updateState");
        this.state = state;
    }

    // </editor-fold>
    ////////////////////////////////////////////////////////////////////////////
}
