package dominion.commands;

import dominion.model.PlayerCard;

public class CurrentPlayer extends Player {

    private PlayerCard[] cards;
    private int actions;
    private int buys;
    //private int coins;

    public PlayerCard[] getCards() {
        return this.cards;
    }

    public void setCards(PlayerCard[] cards) {
        this.cards = cards;
    }

    public int getActions() {
        return this.actions;
    }

    public int getBuys() {
        return this.buys;
    }

    /*public int getCoins() {
        return this.actions;
    }*/

    public void setActions(int value) {
        this.actions = value;
    }

    public void setBuys(int value) {
        this.buys = value;
    }

    /*public void setCoins(int value) {
        this.actions = value;
    }*/
}
