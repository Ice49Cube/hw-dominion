package dominion.model;

import dominion.model.info.CardSet;
import dominion.model.info.CardInfo;
import dominion.model.database.*;

import java.sql.*;
import java.util.*;
import java.util.function.Predicate;

public class Game {
    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Constructors and member declarations" defaultstate="collapsed">

    private final int id;
    private final HashMap<Integer, GameCard> cards;
    private final HashMap<Integer, Player> players;
    private int player;
    private String state;
    private int winner = -1;
    private String cardSet;

    public Game(Connection con, String[] playerNames, String cardSetName) throws Exception {
        CardSet cardSet = CardSet.parse(cardSetName, CardSet.getRandomCardSet());
        this.cardSet = cardSet.getName();
        this.state = "Action";
        this.id = this.insertGameIntoGames(con, this.cardSet);
        this.cards = new HashMap<Integer, GameCard>();
        this.insertCardIntoGameCards(con, "Treasure", CardInfo.Gold, playerNames.length);
        this.insertCardIntoGameCards(con, "Treasure", CardInfo.Silver, playerNames.length);
        GameCard copper = this.insertCardIntoGameCards(con, "Treasure", CardInfo.Copper, playerNames.length);
        this.insertCardIntoGameCards(con, "Victory", CardInfo.Province, playerNames.length);
        this.insertCardIntoGameCards(con, "Victory", CardInfo.Duchy, playerNames.length);
        GameCard estate = this.insertCardIntoGameCards(con, "Victory", CardInfo.Estate, playerNames.length);
        this.insertCardIntoGameCards(con, "Victory", CardInfo.Curse, playerNames.length);
        for (CardInfo cardInfo : cardSet.getCards()) {
            this.insertCardIntoGameCards(con, "Kingdom", cardInfo, playerNames.length);
        }
        this.players = new HashMap<Integer, Player>();
        for (int i = 0; i < playerNames.length; i++) {
            int first = i == 0 ? 1 : 0;
            Player player = this.insertPlayerIntoGame(con, playerNames[i], first, first);
            if (first == 1) {
                this.setCurrentPlayerId(con, player.getId());
            }
            this.players.put(player.getId(), player);
            this.insertCardsIntoPlayerCards(con, player, first == 1, copper.getId(), estate.getId());
        }
    }

    public Game(Connection con, int gameId) throws Exception {
        this.cards = new HashMap<Integer, GameCard>();
        this.players = new HashMap<Integer, Player>();
        this.id = gameId;
        this.loadGame(con);
    }

    // </editor-fold>
    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Public">
    public void cancelActions(Connection con) throws Exception {
        this.updateState(con, "Bet");
    }

    public GameCard getCard(int id) {
        return this.cards.get(id);
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

    public Player getPlayer(int id) {
        return this.players.get(id);
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

    public boolean isFinished(Connection con) throws Exception {
        String sql = "SELECT MAX(counter) as counter FROM" + " ("
                + " SELECT `count` = 0 AS counter FROM gamecards WHERE" + " game = ? AND name = \"province\"" + " UNION"
                + " SELECT COUNT(*) >= 3 AS counter FROM gamecards WHERE" + " game = ? AND `count` = 0" + " ) AS Q";
        try (PreparedStatement stmt = con.prepareStatement(sql);
                ResultSet rs = Database.executeQuery(con, stmt, new Object[]{this.id, this.id})) {
            if (rs.next()) {
                return rs.getInt("counter") == 1;
            } else {
                return false;
            }
        }
    }

    // </editor-fold>
    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Private" defaultstate="collapsed">
    private GameCard insertCardIntoGameCards(Connection con, String deck, CardInfo cardInfo, int numberOfPlayers)
            throws Exception {
        String sql = "INSERT INTO gamecards (game, name, deck, `count`, cost, value, isaction, iscoin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int count = cardInfo.getCount(numberOfPlayers);
        int id = Database.executeInsert(con, sql,
                new Object[]{this.id, cardInfo.getName(), deck, count, cardInfo.getCost(), cardInfo.getValue(),
                    cardInfo.getIsAction() ? 1 : 0, cardInfo.getIsCoin() ? 1 : 0});
        GameCard card = new GameCard(this, id, cardInfo.getName(), deck, count, cardInfo.getCost(), cardInfo.getValue(),
                cardInfo.getIsAction(), cardInfo.getIsCoin());
        this.cards.put(card.getId(), card);
        return card;
    }

    private void insertCardsIntoPlayerCards(Connection con, Player player, boolean first, int copper, int estate)
            throws Exception {
        ArrayList<Integer> randoms = Util.getRandomRange(3, 0, 10);
        for (int i = 0; i < 10; i++) {
            String pile = first ? (i < 5 ? "Deck" : "Hand") : "Deck";
            int cardId = randoms.contains(i) ? estate : copper;
            PlayerCard card = insertCardIntoPlayerCards(con, player, cardId, pile, i);
            player.addCard(card);
        }
        player.setMinOrder(0);
        player.setMaxOrder(9);
    }

    private PlayerCard insertCardIntoPlayerCards(Connection con, Player player, int cardId, String pile, int order)
            throws Exception {
        String sql = "INSERT INTO playercards (player, card, pile, `order`) VALUES(?, ?, ?, ?)";
        int id = Database.executeInsert(con, sql, new Object[]{player.getId(), cardId, pile, order});
        return new PlayerCard(player, id, cardId, pile, order);
    }

    private int insertGameIntoGames(Connection con, String cardSet) throws Exception {
        return Database.executeInsert(con, "INSERT INTO games (cardset, state) VALUES (?, \"Action\")",
                new Object[]{cardSet});
    }

    private Player insertPlayerIntoGame(Connection con, String playerName, int actions, int buys) throws Exception {
        String sql = "INSERT INTO players (game, `name`, actions, buys) VALUES (?, ?, ?, ?)";
        int id = Database.executeInsert(con, sql, new Object[]{this.id, playerName, actions, buys});
        return new Player(this, id, playerName, actions, buys);
    }

    private void loadCards(Connection con) throws Exception {
        String sql = "SELECT * FROM gamecards" + " INNER JOIN games ON game = games.id" + " WHERE games.id = ?"
                + " ORDER BY deck";
        try (PreparedStatement stmt = con.prepareStatement(sql);
                ResultSet rs = Database.executeQuery(con, stmt, new Object[]{this.id})) {
            while (rs.next()) {
                GameCard card = this.readGameCard(rs);
                this.cards.put(card.getId(), card);
            }
        }
    }

    private void loadGame(Connection con) throws Exception {
        loadPlayers(con);
        loadCards(con);
        if (this.players.size() == 0 || this.cards.size() == 0) {
            throw new IllegalStateException("No game data.");
        }
    }

    private void loadPlayers(Connection con) throws Exception {
        String sql = "SELECT * FROM playercards" + " INNER JOIN players ON player = players.id"
                + " INNER JOIN games ON game = games.id" + " INNER JOIN gamecards ON playercards.card = gamecards.id"
                + " WHERE games.id = ?" + " ORDER BY players.id, pile, `order`";
        Player player = null;
        try (PreparedStatement stmt = con.prepareStatement(sql);
                ResultSet rs = Database.executeQuery(con, stmt, new Object[]{this.id})) {
            while (rs.next()) {
                // First time/first loop cycle?
                if (player == null) {
                    readGame(rs);
                }
                int playerId = rs.getInt("players.id");
                if (!players.containsKey(playerId)) {
                    player = readPlayer(rs);
                    players.put(player.getId(), player);
                } else {
                    player = players.get(playerId);
                }
                int order = rs.getInt("order");
                if (player.getMinOrder() > order) {
                    player.setMinOrder(order);
                } else if (player.getMaxOrder() < order) {
                    player.setMaxOrder(order);
                }
                player.addCard(readPlayerCard(rs, player));
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
        return new Player(this, rs.getInt("players.id"), rs.getString("players.name"), rs.getInt("players.actions"),
                rs.getInt("players.buys") /*
											 * , rs.getInt("players.coins")
         */
        );
    }

    private GameCard readGameCard(ResultSet rs) throws Exception {
        return new GameCard(this, rs.getInt("gamecards.id"), rs.getString("gamecards.name"),
                rs.getString("gamecards.deck"), rs.getInt("gamecards.count"), rs.getInt("gamecards.cost"),
                rs.getInt("gamecards.value"), rs.getBoolean("gamecards.isaction"), rs.getBoolean("gamecards.iscoin"));
    }

    private PlayerCard readPlayerCard(ResultSet rs, Player player) throws Exception {
        return new PlayerCard(player, rs.getInt("playercards.id"), rs.getInt("playercards.card"),
                rs.getString("playercards.pile"), rs.getInt("playercards.order"));
    }

    private void setCurrentPlayerId(Connection con, int playerId) throws Exception {
        String sql = "UPDATE games SET player = ? WHERE id = ?";
        int affected = Database.executeUpdate(con, sql, new Object[]{playerId, this.id});
        if (affected != 1) {
            throw new IllegalStateException("Could not update the current player.");
        } else {
            this.player = playerId;
        }
    }
    
    private void updateState(Connection con, String state) throws Exception {
        Database.execute(con, "UPDATE games SET state = ? WHERE id = ?", new Object[]{state, this.id});
        this.state = state;
    }

    // </editor-fold>
    ////////////////////////////////////////////////////////////////////////////
}
