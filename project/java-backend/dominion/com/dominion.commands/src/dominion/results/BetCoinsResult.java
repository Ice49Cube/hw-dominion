package dominion.results;

import dominion.routing.*;

public class BetCoinsResult extends ResultBase {

    private PlayerCardInfo[] playerCards;
    private int coins;

    public BetCoinsResult() {
        super("betCoins");
    }

    public int getCoins() {
        return this.coins;
    }

    public void setCoins(int value) {
        this.coins = value;
    }

    public PlayerCardInfo[] getPlayerCards() {
        return this.playerCards;
    }

    public void setPlayerCards(PlayerCardInfo[] value) {
        this.playerCards = value;
    }

}
