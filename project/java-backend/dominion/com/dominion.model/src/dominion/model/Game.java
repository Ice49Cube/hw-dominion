package dominion.model;

import dominion.model.info.CardSet;
import dominion.model.info.CardInfo;
import dominion.model.database.*;

import java.sql.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Game {
    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Constructors and member declarations" defaultstate="collapsed">

    private final int id;
    private final HashMap<Integer, GameCard> cards;
    private final HashMap<Integer, Player> players;
    private String cardSet;
    private int player;
    private String state;
    private int winner = -1;

    private Game(Connection con, String[] playerNames, CardSet source) throws Exception {
        this.cards = new HashMap<>();
        this.players = new HashMap<>();
        this.cardSet = source.getName();
        this.state = "Action";
        this.id = this.insert(con, this.cardSet);
        this.insertCard(con, "Treasure", CardInfo.Gold, playerNames.length);
        this.insertCard(con, "Treasure", CardInfo.Silver, playerNames.length);
        GameCard copper = this.insertCard(con, "Treasure", CardInfo.Copper, playerNames.length);
        this.insertCard(con, "Victory", CardInfo.Province, playerNames.length);
        this.insertCard(con, "Victory", CardInfo.Duchy, playerNames.length);
        GameCard estate = this.insertCard(con, "Victory", CardInfo.Estate, playerNames.length);
        this.insertCard(con, "Victory", CardInfo.Curse, playerNames.length);
        for (CardInfo cardInfo : source.getCards()) {
            this.insertCard(con, "Kingdom", cardInfo, playerNames.length);
        }
        for (int i = 0; i < playerNames.length; i++) {
            int first = i == 0 ? 1 : 0;
            Player player = this.insertPlayer(con, playerNames[i], first, first);
            if (first == 1) {
                this.updatePlayer(con, player.getId());
            }
            this.players.put(player.getId(), player);
            player.insertCardsOnNewGame(con, first == 1, copper.getId(), estate.getId());
        }
    }

    public Game(Connection con, String[] playerNames, String cardSetName) throws Exception {
        this(con, playerNames, CardSet.parse(cardSetName, CardSet.getRandomCardSet()));
    }

    public Game(Connection con, int gameId) throws Exception {
        this.cards = new HashMap<>();
        this.players = new HashMap<>();
        this.id = gameId;
        this.loadGame(con);
    }

    // </editor-fold>
    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Accessors (Getters)" defaultstate="collapsed">
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

    private Player getNextPlayer() {
        List<Player> sorted = this.players.values().stream().sorted((p1, p2) -> p1.getId() - p2.getId()).collect(Collectors.toList());
        int index = sorted.indexOf(this.getCurrentPlayer());
        index += 1;
        if (index >= sorted.size()) {
            index = 0;
        }
        return sorted.get(index);
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
    // <editor-fold desc="Facade methods" defaultstate="collapsed">

    private int getGameCardValueByPlayerCardId(Player currentPlayer, int playerCardId) {
        PlayerCard playerCard = currentPlayer.getCard(playerCardId);
        Guard.validateNotNull(playerCard, "Card does not belong to the player or does not exist. - Player.getCoinCardValueById");
        Guard.validateTrue(playerCard.getPile().equals("Hand"), "Card is not in the player his hand. - Player.getCoinCardValueByCardId");
        GameCard gameCard = this.getCard(playerCard);
        Guard.validateTrue(gameCard.getIsCoin(), "Card is not a coin. - Player.getCoinCardValueByCardId");
        return gameCard.getValue();
    }

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
        int max = currentPlayer.getMaxOrder();
        for (int i = 0; i < playerCardId.length; i++) {
            PlayerCard playerCard = currentPlayer.getCard(playerCardId[i]);
            // Move from hand to table
            playerCard.updatePileAndOrder(con, "Table", ++max);
        }
        currentPlayer.setMaxOrder(max);
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
        PlayerCard result = currentPlayer.insertCard(con, gameCard.getId(), "Discard", currentPlayer.getMaxOrder() + 1);
        return result;
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

    // </editor-fold>
    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Update methods" defaultstate="collapsed">
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
    // <editor-fold desc="Create new or read existing game"
    // defaultstate="collapsed">
    private GameCard insertCard(Connection con, String deck, CardInfo cardInfo, int numberOfPlayers) throws Exception {
        String sql = "INSERT INTO gamecards (game, name, deck, `count`, cost, value, isaction, iscoin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int count = cardInfo.getCount(numberOfPlayers);
        int recordId = Database.executeInsert(con, sql, new Object[]{this.id, cardInfo.getName(), deck, count, cardInfo.getCost(), cardInfo.getValue(), cardInfo.getIsAction() ? 1 : 0, cardInfo.getIsCoin() ? 1 : 0});
        GameCard card = new GameCard(this, recordId, cardInfo.getName(), deck, count, cardInfo.getCost(), cardInfo.getValue(), cardInfo.getIsAction(), cardInfo.getIsCoin());
        this.cards.put(card.getId(), card);
        return card;
    }

    private int insert(Connection con, String cardSet) throws Exception {
        return Database.executeInsert(con, "INSERT INTO games (cardset, state) VALUES (?, \"Action\")", new Object[]{cardSet});
    }

    
    private Player insertPlayer(Connection con, String playerName, int actions, int buys) throws Exception {
        String sql = "INSERT INTO players (game, `name`, actions, buys) VALUES (?, ?, ?, ?)";
        int recordId = Database.executeInsert(con, sql, new Object[]{this.id, playerName, actions, buys});
        return new Player(this, recordId, playerName, actions, buys, 0);
    }

    private void loadCards(Connection con) throws Exception {
        String sql = "SELECT * FROM gamecards INNER JOIN games ON game = games.id WHERE games.id = ? ORDER BY deck";
        try (PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = Database.executeQuery(con, stmt, new Object[]{this.id})) {
            while (rs.next()) {
                GameCard card = this.readGameCard(rs);
                this.cards.put(card.getId(), card);
            }
        }
    }

    private void loadGame(Connection con) throws Exception {
        loadPlayers(con);
        loadCards(con);
        if (this.players.isEmpty() || this.cards.isEmpty()) {
            throw new IllegalStateException("No game data.");
        }
    }

    private void loadPlayers(Connection con) throws Exception {
        String sql = "SELECT * FROM playercards" + " INNER JOIN players ON player = players.id"
                + " INNER JOIN games ON game = games.id"
                + " INNER JOIN gamecards ON playercards.card = gamecards.id"
                + " WHERE games.id = ?" + " ORDER BY players.id, pile, `order`";
        Player current = null;
        try (PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = Database.executeQuery(con, stmt, new Object[]{this.id})) {
            while (rs.next()) {
                // First time/first loop cycle?
                if (current == null) {
                    readGame(rs);
                }
                int playerId = rs.getInt("players.id");
                if (!players.containsKey(playerId)) {
                    current = readPlayer(rs);
                    players.put(current.getId(), current);
                } else {
                    current = players.get(playerId);
                }
                int order = rs.getInt("order");
                if (current.getMinOrder() > order) {
                    current.setMinOrder(order);
                } else if (current.getMaxOrder() < order) {
                    current.setMaxOrder(order);
                }
                current.addCard(readPlayerCard(rs, current));
            }
        }
    }

    private void readGame(ResultSet rs) throws Exception {
        this.cardSet = rs.getString("games.cardSet");
        this.player = rs.getInt("games.player");
        this.state = rs.getString("games.state");
        this.winner = rs.getInt("games.winner");
    }

    private Player readPlayer(ResultSet rs) throws Exception {
        return new Player(this, rs.getInt("players.id"), rs.getString("players.name"), rs.getInt("players.actions"), rs.getInt("players.buys"), rs.getInt("players.coins"));
    }

    private GameCard readGameCard(ResultSet rs) throws Exception {
        return new GameCard(this, rs.getInt("gamecards.id"), rs.getString("gamecards.name"), rs.getString("gamecards.deck"), rs.getInt("gamecards.count"), rs.getInt("gamecards.cost"), rs.getInt("gamecards.value"), rs.getBoolean("gamecards.isaction"), rs.getBoolean("gamecards.iscoin"));
    }

    private PlayerCard readPlayerCard(ResultSet rs, Player player) throws Exception {
        return new PlayerCard(player, rs.getInt("playercards.id"), rs.getInt("playercards.card"), rs.getString("playercards.pile"), rs.getInt("playercards.order"));
    }
    // </editor-fold>
    ////////////////////////////////////////////////////////////////////////////
}
/*
 * 
 * public boolean isFinished(Connection con) throws Exception { return
 * this.winner != -1;
 * 
 * /*String sql = "SELECT MAX(counter) as counter FROM" + " (" +
 * " SELECT `count` = 0 AS counter FROM gamecards WHERE" +
 * " game = ? AND name = \"province\"" + " UNION" +
 * " SELECT COUNT(*) >= 3 AS counter FROM gamecards WHERE" +
 * " game = ? AND `count` = 0" + " ) AS Q"; try (PreparedStatement stmt =
 * con.prepareStatement(sql); ResultSet rs = Database.executeQuery(con, stmt,
 * new Object[]{this.id, this.id})) { if (rs.next()) { return
 * rs.getInt("counter") == 1; } else { return false; } } }
 */





