package dominion.results;

import dominion.model.*;

public class GameInfo {

    private int id;
    private String cardSet;
    private int player;
    private String state;

    public static GameInfo fromGame(Game game) {
        GameInfo result = new GameInfo();
        result.setId(game.getId());
        result.setCardSet(game.getCardSet());
        result.setPlayer(game.getCurrentPlayerId());
        result.setState(game.getState());
        return result;
    }

    public int getId() {
        return this.id;
    }

    public String getCardSet() {
        return this.cardSet;
    }

    public int getPlayer() {
        return this.player;
    }

    public String getState() {
        return this.state;
    }

    public void setId(int value) {
        this.id = value;
    }

    public void setCardSet(String value) {
        this.cardSet = value;
    }

    public void setPlayer(int value) {
        this.player = value;
    }

    public void setState(String value) {
        this.state = value;
    }

}
