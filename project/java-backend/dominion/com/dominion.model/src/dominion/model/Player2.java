package dominion.model;

import dominion.model.database.*;

import java.util.*;
import java.util.function.*;
import java.sql.*;

public class Player2 {

    private final Game2 game;
    private final int id;
    private final String name;
    private final HashMap<Integer, PlayerCard2> cards;
    private int actions;
    private int buys;
    private int coins;
    private int minOrder;
    private int maxOrder;

    private Player2(Game2 game, int id, String name, int actions, int buys, int coins) {
        this.game = game;
        this.id = id;
        this.name = name;
        this.buys = buys;
        this.coins = coins;
        this.actions = actions;
        this.cards = new HashMap<>();
        this.minOrder = 0;
        this.maxOrder = -1;
    }

    static Player2 create(Connection con, Game2 game, String playerName, GameCard2 estate, GameCard2 copper) throws Exception {
        String sql = "INSERT INTO players (game, `name`) VALUES (?, ?)";
        Object[] args = new Object[]{game.getId(), playerName};
        int recordId = Database.executeInsert(con, sql, args);
        Player2 player = new Player2(game, recordId, playerName, 1, 1, 0);
        ArrayList<Integer> randoms = Util.getRandomRange(3, 0, 10);
        for (int i = 0; i < 10; i++) {
            String pile = i < 5 ? "Deck" : "Hand";
            int cardId = randoms.contains(i) ? estate.getId() : copper.getId();
            player.addCard(PlayerCard2.create(con, player, cardId, pile, player.newMaxOrder()));
        }
        return player;
    }

    static Player2 load(Game2 game, ResultSet rs) throws Exception {
        return new Player2(game, rs.getInt("players.id"), rs.getString("players.name"), rs.getInt("players.actions"), rs.getInt("players.buys"), rs.getInt("players.coins"));
    }

    PlayerCard2 addCard(PlayerCard2 value) {
        Guard.validateNull(this.cards.put(value.getId(), value), "Card already in game. - Game.addCard");
        return value;
    }
    
    public int newMaxOrder() {
        return ++this.maxOrder;
    }
    
    public int newMinOrder() {
        return ++this.minOrder;
    }
    
    public int getActions() {
        return this.actions;
    }

    public int getBuys() {
        return this.buys;
    }

    public PlayerCard2 getCard(int id) {
        return this.cards.get(id);
    }

    public PlayerCard2 getCard(Predicate<? super PlayerCard2> filter) {
        Optional<PlayerCard2> result = this.cards.values().stream().filter(filter).findFirst();
        return result.isPresent() ? result.get() : null;
    }

    public PlayerCard2[] getCards() {
        return this.cards.values().toArray(new PlayerCard2[this.cards.size()]);
    }

    public PlayerCard2[] getCards(Predicate<? super PlayerCard2> filter) {
        return this.cards.values().stream().filter(filter).toArray(size -> new PlayerCard2[size]);
    }

    public int getCoins() {
        return this.coins;
    }

    public Game2 getGame() {
        return this.game;
    }
    
    public int getId() {
        return this.id;
    }

    public boolean getIsCurrent() {
        return this.id == game.getCurrentPlayerId();
    }

    public int getMaxOrder() {
        return this.maxOrder;
    }

    public int getMinOrder() {
        return this.minOrder;
    }

    public String getName() {
        return this.name;
    }

    void discardCards(Connection con, Predicate<? super PlayerCard2> filter) throws Exception {
        this.cards.values().stream().filter(filter).sorted((c1, c2) -> c1.getOrder() - c2.getOrder()).forEach(c -> {
            try {
                c.updatePileAndOrder(con, "Discard", ++this.maxOrder);
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
