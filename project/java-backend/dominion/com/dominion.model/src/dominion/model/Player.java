package dominion.model;

import com.fasterxml.jackson.annotation.*;

import dominion.model.database.*;

import java.util.*;
import java.util.function.*;
import java.sql.*;

public class Player {

    @JsonIgnore()
    private final Game game;
    private final int id;
    private final String name;
    private final HashMap<Integer, PlayerCard> cards;
    private int actions;
    private int buys;
    private int coins;
    private int minOrder;
    private int maxOrder;

    public Player(Game game, int id, String name, int actions, int buys, int coins) {
        this.game = game;
        this.id = id;
        this.name = name;
        this.buys = buys;
        this.coins = coins;
        this.actions = actions;
        this.cards = new HashMap<>();
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

    public int getMaxOrder() {
        return this.maxOrder;
    }

    public int getMinOrder() {
        return this.minOrder;
    }

    public String getName() {
        return this.name;
    }

    void setMaxOrder(int value) {
        this.maxOrder = value;
    }

    void setMinOrder(int value) {
        this.minOrder = value;
    }
    
    void addCard(PlayerCard card) {
        this.cards.put(card.getId(), card);
    }

    void insertCardsOnNewGame(Connection con, boolean first, int copperId, int estateId) throws Exception {
        ArrayList<Integer> randoms = Util.getRandomRange(3, 0, 10);
        for (int i = 0; i < 10; i++) {
            String pile = i < 5 ? "Deck" : "Hand";
            int cardId = randoms.contains(i) ? estateId : copperId;
            this.insertCard(con, cardId, pile, i);
        }
        this.minOrder = 0;
        this.maxOrder = 9;
    }
    
    PlayerCard insertCard(Connection con, int gameCardId, String pile, int order) throws Exception {
        String sql = "INSERT INTO playercards (player, card, pile, `order`) VALUES(?, ?, ?, ?)";
        Object[] args = new Object[]{this.id, gameCardId, pile, order};
        int recordId = Database.executeInsert(con, sql, args);
        PlayerCard result = new PlayerCard(this, recordId, gameCardId, pile, order);
        this.cards.put(recordId, result);
        return result;
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
