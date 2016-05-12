package dominion.commands;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;

import dominion.routing.CommandBase;

public class PlayActionCommand extends CommandBase {

	private boolean cancel;
    private int cardId;
    private int gameId;
    private int playerId;
    private Object data;
    
    public boolean getCancel() {
        return this.cancel;
    }
    
    public int getCardId() {
        return this.cardId;
    }
    
    @JsonRawValue()
    public String getData() {
    	return this.data.toString();
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
    
    public void setData(JsonNode value) {
    	this.data = value;
    }
    public void setGameId(int value) {
        this.gameId = value;
    }
    
    public void setPlayerId(int value) {
        this.playerId = value;
    }
}
