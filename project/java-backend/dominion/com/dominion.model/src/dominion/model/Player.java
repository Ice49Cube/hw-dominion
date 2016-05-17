package dominion.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dominion.model.database.Database;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Comparator;

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
        this.cards = new HashMap();
    }

    void addCard(PlayerCard card) {
        this.cards.put(card.getId(), card);
    }

    public void betCoins(Connection con, int[] playerCardId) throws Exception {
        Guard.validateState(game, "Bet");
        Guard.validateCurrentPlayer(this, "betCoins");
        // Verify if it are coin cards...
        int total = 0;
        for (int i = 0; i < playerCardId.length; i++) {
            total += getCoinCardValueByCardId(playerCardId[i]);
        }
        // Verified, update the cards and the player his coin value...
        int affected;
        int max = this.getMaxOrder();
        for (int i = 0; i < playerCardId.length; i++) {
            PlayerCard playerCard = this.getCard(playerCardId[i]);
            // Remove from hand to table
            max += 1;
            affected = Database.executeUpdate(con, "UPDATE playercards SET pile = \"Table\", `order` = ? WHERE id = ?", new Object[]{max, playerCard.getId()});
            Guard.validateAffected(1, affected, "Game.Player.betCoins() - playercards");
            playerCard.setPile("Table");
            playerCard.setOrder(max);
        }
        this.setMaxOrder(max);
        affected = Database.executeUpdate(con, "UPDATE players SET coins = coins + ? WHERE id = ?", new Object[]{total, this.id});
        Guard.validateAffected(1, affected, "Game.Player.betCoins() - players");
        this.coins += total;
    }

    public PlayerCard buyCard(Connection con, GameCard card) throws Exception {
        Guard.validateCurrentPlayer(this, "buyCard");
        Guard.validateState(game, "Buy");
        if (card.getCost() > this.getCoins()) {
            throw new IllegalStateException("Player is too poor. - Player.buyCard");
        }
        if (card.getCount() <= 0) {
            throw new IllegalStateException("No more cards available. - Player.buyCard");
        }
        //
        String sql = "UPDATE gamecards SET count = count - 1 WHERE id = ?";
        Object[] args = new Object[]{card.getId()};
        int affected = Database.executeUpdate(con, sql, args);
        Guard.validateAffected(1, affected, "Game.Player.buyCard - gamecards");
        card.setCount(card.getCount() - 1);
        //
        int temp = this.coins - card.getCost();
        sql = "UPDATE players SET coins = ?, buys = buys - 1 WHERE id = ?";
        args = new Object[]{temp, this.id};
        affected = Database.executeUpdate(con, sql, args);
        Guard.validateAffected(1, affected, "Game.Player.buyCard - players");
        this.coins = temp;
        //
        int newOrder = this.getMaxOrder() + 1;
        sql = "INSERT INTO playercards (player, card, `order`, pile) VALUES (?, ?, ?, \"Discard\")";
        args = new Object[]{this.getId(), card.getId(), newOrder};
        int recordId = Database.executeInsert(con, sql, args);
        this.setMaxOrder(newOrder);
        PlayerCard result = new PlayerCard(this, recordId, card.getId(), "Discard", newOrder);
        this.addCard(result);
        return result;
    }

    public void cancelActions(Connection con) throws Exception {
        Guard.validateCurrentPlayer(this, "cancelActions");
        Guard.validateState(game, "Action");
        game.updateState(con, "Bet");
    }

    public void cancelBet(Connection con) throws Exception {
        Guard.validateCurrentPlayer(this, "cancelBet");
        Guard.validateState(game, "Bet");
        game.updateState(con, "Buy");
    }

    public void cancelBuy(Connection con) throws Exception {
        Guard.validateCurrentPlayer(this, "cancelBuy");
        Guard.validateState(game, "Buy");
        game.nextPlayer(con);
    }

    public int getCoinCardValueByCardId(int playerCardId) {
        PlayerCard playerCard = this.getCard(playerCardId);
        Guard.validateNotNull(playerCard, "Card does not belong to the player or does not exist. - Player.getCoinCardValueById");
        Guard.validateTrue(playerCard.getPile().equals("Hand"), "Card is not in the player his hand. - Player.getCoinCardValueByCardId");
        GameCard gameCard = game.getCard(playerCard.getCardId());
        Guard.validateTrue(gameCard.getIsCoin(), "Card is not a coin. - Player.getCoinCardValueByCardId");
        return gameCard.getValue();
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
        return this.cards.values().stream().filter(filter).findFirst().get();
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
    
    void updateActions(int value) {
        this.actions = value;
    }

    void updateBuys(int value) {
        this.buys = value;
    }
    
    void updateCoins(int value) {
        this.coins = value;
    }
    
    void updateAll(int buys, int coins, int actions) {
        this.updateActions(actions);
        this.updateBuys(buys);
        this.updateCoins(coins);
    }
}
