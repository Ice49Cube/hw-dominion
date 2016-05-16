package dominion.results;

import dominion.model.*;
import java.util.*;
import java.util.function.*;

public class CurrentPlayerInfo {/* extends PlayerInfo {

    private HashMap<Integer, PlayerCard> cards;
    private int actions;
    private int buys;
    // private int coins;

    public PlayerCard[] getCards(Predicate<? super PlayerCard> filter) {
        return this.cards.values().stream().filter(filter).toArray(size -> new PlayerCard[size]);
    }

    public PlayerCard[] getCards() {
        return this.cards == null ? null : this.cards.values().toArray(new PlayerCard[this.cards.size()]);
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

    public int getActions() {
        return this.actions;
    }

    public int getBuys() {
        return this.buys;
    }

    /*
	 * public int getCoins() { return this.actions; }
     /
    public void setActions(int value) {
        this.actions = value;
    }

    public void setBuys(int value) {
        this.buys = value;
    }

    /*
	 * public void setCoins(int value) { this.actions = value; }
     */
}
