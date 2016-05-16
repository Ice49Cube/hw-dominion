package dominion.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Player {

	@JsonIgnore()
    private final Game game;
    private final int id;
    private final String name;
    private final int actions;
    private final int buys;
    //private final int coins;
    private final HashMap<Integer, PlayerCard> cards;
    private int minOrder;
    private int maxOrder;

    public Player(Game game, int id, String name, int actions, int buys) {//, int coins) {
        this.game = game;
        this.id = id;
        this.name = name;
        this.buys = buys;
        //this.coins = coins;
        this.actions = actions;
        this.cards = new HashMap();
    }

    public void addCard(PlayerCard card) {
        this.cards.put(card.getId(), card);
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
        return this.cards.values().stream().filter(c -> c.getPile().equals("Hand")).map((PlayerCard t) -> this.game.getCard(t.getCardId()).getValue()).mapToInt(Integer::intValue).sum();
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

    public void setMaxOrder(int value) {
        this.maxOrder = value;
    }

    public void setMinOrder(int value) {
        this.minOrder = value;
    }
}
