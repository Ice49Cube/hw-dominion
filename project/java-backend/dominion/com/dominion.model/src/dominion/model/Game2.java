package dominion.model;

import dominion.model.database.*;
import dominion.model.info.*;

import java.sql.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Game2 {

    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Constructor, create, load and member declarations" defaultstate="collapsed">
    private final int id;
    private final String cardSet;
    private final HashMap<Integer, GameCard2> cards;
    private final HashMap<Integer, Player2> players;
    private int player;
    private String state;
    private int winner;

    private Game2(int id, String cardSet) {
        this(id, cardSet, -1, null, -1);
    }

    private Game2(int id, String cardSet, int player, String state, int winner) {
        this.id = id;
        this.cardSet = cardSet;
        this.state = state;
        this.winner = winner;
        this.cards = new HashMap<>();
        this.players = new HashMap<>();
    }

    public static Game2 create(Connection con, String[] playerNames, String cardSet) throws Exception {
        CardSet set = CardSet.parse(cardSet, CardSet.Random);
        Game2 game = Game2.insert(con, set.getName());
        game.state = "Action";
        game.addCard(GameCard2.create(con, game, "Treasure", CardInfo.Gold, playerNames.length));
        game.addCard(GameCard2.create(con, game, "Treasure", CardInfo.Silver, playerNames.length));
        GameCard2 copper = game.addCard(GameCard2.create(con, game, "Treasure", CardInfo.Copper, playerNames.length));
        game.addCard(GameCard2.create(con, game, "Victory", CardInfo.Province, playerNames.length));
        game.addCard(GameCard2.create(con, game, "Victory", CardInfo.Duchy, playerNames.length));
        GameCard2 estate = game.addCard(GameCard2.create(con, game, "Victory", CardInfo.Estate, playerNames.length));
        game.addCard(GameCard2.create(con, game, "Victory", CardInfo.Curse, playerNames.length));
        CardInfo[] cards = set.getCards();
        for (CardInfo cardInfo : cards) {
            game.addCard(GameCard2.create(con, game, "Kingdom", cardInfo, playerNames.length));
        }
        // Could make first player random...
        for (int i = 0; i < playerNames.length; i++) {
            Player2 newPlayer = game.addPlayer(Player2.create(con, game, playerNames[i], estate, copper));
            if (i == 0) {
                game.updatePlayer(con, newPlayer.getId());
            }
        }
        return game;
    }

    private static Game2 insert(Connection con, String cardSet) throws Exception {
        String sql = "INSERT INTO games (cardset, state) VALUES (?, \"Action\")";
        Object[] args = new Object[]{cardSet};
        int id = Database.executeInsert(con, sql, args);
        return new Game2(id, cardSet);
    }

    public static Game2 load(Connection con, int id) throws Exception {
        String sql = "SELECT * FROM playercards"
                + " INNER JOIN players ON playercards.player = players.id"
                + " INNER JOIN games ON players.game = games.id"
                + " INNER JOIN gamecards ON playercards.card = gamecards.id"
                + " WHERE games.id = ?"
                + " ORDER BY players.id, playercards.`order`, playercards.pile";
        Object[] args = new Object[]{id};
        Game2 game = null;
        try (PreparedStatement stmt = con.prepareStatement(sql);
                ResultSet rs = Database.executeQuery(con, stmt, args)) {
            while (rs.next()) {
                if (game == null) {
                    game = Game2.load(rs);
                }
                int playerId = rs.getInt("players.id");
                Player2 player;
                if (!game.players.containsKey(playerId)) {
                    player = game.addPlayer(Player2.load(game, rs));
                } else {
                    player = game.players.get(playerId);
                }
                player.addCard(PlayerCard2.load(player, rs));
                GameCard2 gameCard = GameCard2.load(game, rs);
            }
        }
        return game;
    }

    private static Game2 load(ResultSet rs) throws Exception {
        return new Game2(rs.getInt("games.id"), rs.getString("games.cardSet"), rs.getInt("games.player"), rs.getString("games.state"), rs.getInt("games.winner"));
    }

    // </editor-fold>
    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Public methods and accessors" defaultstate="collapsed">
    public void betCoins(Connection con, int[] playerCardId) throws Exception {
        // Guard.validateCurrentPlayer(this, "betCoins");
        Guard.validateState(this, "Bet");
        // Validates it are are coin cards at the same time...
        Player2 currentPlayer = this.getCurrentPlayer();
        int total = 0;
        for (int i = 0; i < playerCardId.length; i++) {
            total += getGameCardValueByPlayerCardId(currentPlayer, playerCardId[i]);
        }
        // Verified, update the cards and the player his coin value...
        for (int i = 0; i < playerCardId.length; i++) {
            PlayerCard2 playerCard = currentPlayer.getCard(playerCardId[i]);
            // Move from hand to table
            playerCard.updatePileAndOrder(con, "Table", currentPlayer.newMaxOrder());
        }
        currentPlayer.updateCoins(con, currentPlayer.getCoins() + total);
    }

    public PlayerCard2 buyCard(Connection con, int gameCardId) throws Exception {
        // Guard.validateCurrentPlayer(this, "buyCard");
        Guard.validateState(this, "Buy");
        // Get card, validate card...
        GameCard2 gameCard = this.getCard(gameCardId);
        Guard.validateNotNull(gameCard, "Card not in game. - Game.buyCard");
        Guard.validateTrue(gameCard.getCount() > 0, "No more cards available. - Game.buyCard");
        // Get current player, validate player...
        Player2 currentPlayer = this.getCurrentPlayer();
        Guard.validateTrue(currentPlayer.getCoins() >= gameCard.getCost(), "Current player is too poor. - Game.buyCard");
        // Update...
        gameCard.updateCount(con, gameCard.getCount() - 1);
        currentPlayer.updateCoins(con, currentPlayer.getCoins() - gameCard.getCost());
        return currentPlayer.addCard(PlayerCard2.create(con, currentPlayer, gameCardId, "Discard", currentPlayer.newMaxOrder()));
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
        Player2 current = this.getCurrentPlayer();
        current.discardCards(con, c -> !c.getPile().equals("Deck"));

        Player2 next = this.getNextPlayer();
        next.updateAll(con, 1, 1, 0);
        this.updatePlayerAndState(con, next.getId(), "Action");
    }

    public GameCard2 getCard(int id) {
        return this.cards.get(id);
    }

    public GameCard2 getCard(PlayerCard2 playerCard) {
        return this.cards.get(playerCard.getCardId());
    }

    public GameCard2[] getCards() {
        return cards.values().toArray(new GameCard2[cards.size()]);
    }

    public GameCard2[] getCards(Predicate<? super GameCard2> filter) {
        return this.cards.values().stream().filter(filter).toArray(size -> new GameCard2[size]);
    }

    public String getCardSet() {
        return this.cardSet;
    }

    public Player2 getCurrentPlayer() {
        return this.players.get(this.player);
    }

    public int getCurrentPlayerId() {
        return this.player;
    }

    public int getId() {
        return this.id;
    }

    private Player2 getNextPlayer() {
        List<Player2> sorted = this.players.values().stream().sorted((p1, p2) -> p1.getId() - p2.getId()).collect(Collectors.toList());
        int index = sorted.indexOf(this.getCurrentPlayer());
        index += 1;
        if (index >= sorted.size()) {
            index = 0;
        }
        return sorted.get(index);
    }

    public Player2 getPlayer(int id) {
        return this.players.get(id);
    }

    public Player2 getPlayer(Predicate<? super Player2> filter) {
        Optional<Player2> result = this.players.values().stream().filter(filter).findFirst();
        return result.isPresent() ? result.get() : null;
    }

    public Player2[] getPlayers() {
        return this.players.values().toArray(new Player2[this.players.size()]);
    }

    public Player2[] getPlayers(Predicate<? super Player2> filter) {
        return this.players.values().stream().filter(filter).toArray(size -> new Player2[size]);
    }

    public String getState() {
        return this.state;
    }

    // </editor-fold>
    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Private methods" defaultstate="collapsed">
    private GameCard2 addCard(GameCard2 value) {
        Guard.validateNull(this.cards.put(value.getId(), value), "Card already in game. - Game.addCard");
        return value;
    }

    private Player2 addPlayer(Player2 value) {
        Guard.validateNull(this.players.put(value.getId(), value), "Player already in game. - Game.addPlayer");
        return value;
    }

    private int getGameCardValueByPlayerCardId(Player2 currentPlayer, int playerCardId) {
        PlayerCard2 playerCard = currentPlayer.getCard(playerCardId);
        Guard.validateNotNull(playerCard, "Card does not belong to the player or does not exist. - Player2.getCoinCardValueById");
        Guard.validateTrue(playerCard.getPile().equals("Hand"), "Card is not in the player his hand. - Player2.getCoinCardValueByCardId");
        GameCard2 gameCard = this.getCard(playerCard);
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
