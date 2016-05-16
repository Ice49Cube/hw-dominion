package dominion.commands;

import dominion.routing.CommandBase;

public class BuyCardCommand extends CommandBase
{
    private boolean cancel;
    private int cardId;
    private int gameId;
    private int playerId;
    private int[] usedPlayerCardId;
    
    public BuyCardCommand() {
        super("buyCard");
    }
    
    public boolean getCancel() {
        return this.cancel;
    }
    
    public int getCardId() {
        return this.cardId;
    }
    
    public int getGameId() {
        return this.gameId;
    }
    
    public int getPlayerId() {
        return this.playerId;
    }
    
    public void setCancel(boolean value) {
        this.cancel = value;
    }
    
    public void setCardId(int value) {
        this.cardId = value;
    }
    
    public int[] getUsedPlayerCardId() {
    	return this.usedPlayerCardId;
    }

    public void setUsedPlayerCardId(int[] value) {
    	this.usedPlayerCardId = value;
    }
    
    public void setGameId(int value) {
        this.gameId = value;
    }
    
    public void setPlayerId(int value) {
        this.playerId = value;
    }
    
}
