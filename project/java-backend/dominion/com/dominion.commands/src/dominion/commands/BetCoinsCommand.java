package dominion.commands;

import dominion.results.*;
import dominion.routing.*;

public class BetCoinsCommand extends CommandBase {

    private boolean cancel;
    private int gameId;
    private int playerId;
    private int[] coinCards;

    public BetCoinsCommand() {
        super("betCoins");
    }

    public boolean getCancel() {
        return this.cancel;
    }
    
    public void setCancel(boolean value) {
        this.cancel = value;
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

    public int[] getCoinCards() {
        return this.coinCards;
    }

    public void setCoinCards(int[] value) {
        this.coinCards = value;
    }
}
