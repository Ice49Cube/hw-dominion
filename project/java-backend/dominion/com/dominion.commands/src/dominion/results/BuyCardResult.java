package dominion.results;

import dominion.routing.*;

public class BuyCardResult extends ResultBase {

    private PlayerCardInfo playerCard;
    private GameCardInfo gameCard;

    public BuyCardResult() {
        super("buyCard");
    }

    public PlayerCardInfo getPlayerCard() {
        return this.playerCard;
    }

    public GameCardInfo getGameCard() {
        return this.gameCard;
    }

    public void setPlayerCard(PlayerCardInfo value) {
        this.playerCard = value;
    }

    public void setGameCard(GameCardInfo value) {
        this.gameCard = value;
    }

}
