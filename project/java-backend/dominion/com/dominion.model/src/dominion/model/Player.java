package dominion.model;

import dominion.model.database.*;

import java.util.*;
import java.util.function.*;
import java.sql.*;

public class Player {

    private final Game game;
    private final int id;
    private final String name;
    private final HashMap<Integer, PlayerCard> cards;
    private int actions;
    private int buys;
    private int coins;
    private Order order;
    
    private Player(Game game, int id, String name, int actions, int buys, int coins) {
        this.game = game;
        this.id = id;
        this.name = name;
        this.buys = buys;
        this.coins = coins;
        this.actions = actions;
        this.cards = new HashMap<>();
        this.order = new Order();
    }

    static Player create(Connection con, Game game, String playerName, GameCard estate, GameCard copper) throws Exception {
        String sql = "INSERT INTO players (game, `name`) VALUES (?, ?)";
        Object[] args = new Object[]{game.getId(), playerName};
        int recordId = Database.executeInsert(con, sql, args);
        Player player = new Player(game, recordId, playerName, 1, 1, 0);
        ArrayList<Integer> randoms = Util.getRandomRange(3, 0, 10);
        for (int i = 0; i < 10; i++) {
            String pile = i < 5 ? "Deck" : "Hand";
            int cardId = randoms.contains(i) ? estate.getId() : copper.getId();
            player.addCard(PlayerCard.create(con, player, cardId, pile, player.getOrder().newMax()));
        }
        return player;
    }

    static Player load(Game game, ResultSet rs) throws Exception {
        return new Player(game, rs.getInt("players.id"), rs.getString("players.name"), rs.getInt("players.actions"), rs.getInt("players.buys"), rs.getInt("players.coins"));
    }

    PlayerCard addCard(PlayerCard value) {
        Guard.validateNull(this.cards.put(value.getId(), value), "Card already in game. - Game.addCard");
        return value;
    }
    
    public int getActions() {
        return this.actions;
    }

    public int getBuys() {
        return this.buys;
    }

    public PlayerCard getCard(int id) {
        return this.cards.get(id);
    }

    public PlayerCard getCard(Predicate<? super PlayerCard> filter) {
        Optional<PlayerCard> result = this.cards.values().stream().filter(filter).findFirst();
        return result.isPresent() ? result.get() : null;
    }

    public PlayerCard[] getCards() {
        return this.cards.values().toArray(new PlayerCard[this.cards.size()]);
    }

    public PlayerCard[] getCards(Predicate<? super PlayerCard> filter) {
        return this.cards.values().stream().filter(filter).toArray(size -> new PlayerCard[size]);
    }

    public int getCoins() {
        return this.coins;
    }

    public Game getGame() {
        return this.game;
    }
    
    public int getId() {
        return this.id;
    }

    public boolean getIsCurrent() {
        return this.id == game.getCurrentPlayerId();
    }

    public String getName() {
        return this.name;
    }

    public Order getOrder() {
        return this.order;
    }
    public void setOrder(Order value) {
        Guard.validateNotNull(value, "Order can not be null. - Player.setOrder");
        this.order = value;
    }
    
    void discardCards(Connection con, Predicate<? super PlayerCard> filter) throws Exception {
        this.cards.values().stream().filter(filter).sorted((c1, c2) -> c1.getOrder() - c2.getOrder()).forEach(c -> {
            try {
                c.updatePileAndOrder(con, "Discard", this.getOrder().newMax());
            } catch (Exception e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        });
    }
    
    void updateActions(Connection con, int value) throws Exception {
        String sql = "UPDATE players SET actions = ? WHERE id = ?";
        Object[] args = new Object[]{value, this.id};
        int affected = Database.executeUpdate(con, sql, args);
        Guard.validateAffected(1, affected, "Player.updateActions");
        this.actions = value;
    }

    void updateBuys(Connection con, int value) throws Exception {
        String sql = "UPDATE players SET buys = ? WHERE id = ?";
        Object[] args = new Object[]{value, this.id};
        int affected = Database.executeUpdate(con, sql, args);
        Guard.validateAffected(1, affected, "Player.updateBuys");
        this.buys = value;
    }
    
    void updateCoins(Connection con, int value) throws Exception {
        String sql = "UPDATE players SET coins = ? WHERE id = ?";
        Object[] args = new Object[]{value, this.id};
        int affected = Database.executeUpdate(con, sql, args);
        Guard.validateAffected(1, affected, "Player.updateCoins");
        this.coins = value;
    }
    
    void updateAll(Connection con, int actions, int buys, int coins) throws Exception {
        String sql = "UPDATE players SET actions = ?, buys = ?, coins = ? WHERE id = ?";
        Object[] args = new Object[]{actions, buys, coins, this.id};
        int affected = Database.executeUpdate(con, sql, args);
        Guard.validateAffected(1, affected, "Player.updateAll");
        this.actions = actions;
        this.buys = buys;
        this.coins = coins;
    }
}
