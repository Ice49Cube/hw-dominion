package dominion.frontend.responses;

import java.util.*;
import java.util.function.Predicate;

public class Player {

    private int id;
    private String name;
    private HashMap<Integer, PlayerCard> cards;
    private int actions;
    private int buys;

    public PlayerCard[] getCards(Predicate<? super PlayerCard> filter) {
        return this.cards.values().stream().filter(filter).toArray(size -> new PlayerCard[size]);
    }
    
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public PlayerCard[] getCards() {
        return this.cards == null ? null : this.cards.values().toArray(new PlayerCard[this.cards.size()]);
    }

    public int getActions() {
        return this.actions;
    }

    public int getBuys() {
        return this.buys;
    }

    public void setId(int value) {
        this.id = value;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setCards(PlayerCard[] value) {
        if (value != null) {
            this.cards = new HashMap<>();
            for(int i = 0; i < value.length; i++) {
               this.cards.put(value[i].getId(), value[i]);
            }
        } else {
            this.cards = null;
        }
    }

    public void setActions(int value) {
        this.actions = value;
    }

    public void setBuys(int value) {
        this.buys = value;
    }

}
