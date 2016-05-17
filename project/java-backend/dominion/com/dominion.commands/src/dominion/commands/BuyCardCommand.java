package dominion.commands;

import dominion.routing.CommandBase;

public class BuyCardCommand extends CommandBase {

    private boolean cancel;
    private int cardId;
    private int gameId;
    private int playerId;

    public BuyCardCommand() {
        super("buyCard");
    }

    public boolean getCancel() {
        return this.cancel;
    }

    public void setCancel(boolean value) {
        this.cancel = value;
    }

    public int getCardId() {
        return this.cardId;
    }

    public void setCardId(int value) {
        this.cardId = value;
    }

    public int getGameId() {
        return this.gameId;
    }

    public void setGameId(int value) {
        this.gameId = value;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public void setPlayerId(int value) {
        this.playerId = value;
    }
}
