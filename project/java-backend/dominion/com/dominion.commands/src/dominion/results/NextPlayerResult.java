package dominion.results;

import dominion.routing.ResultBase;

public class NextPlayerResult extends ResultBase {

    private PlayerInfo player;

    public NextPlayerResult() {
        super("nextPlayer");
    }

    public PlayerInfo getPlayer() {
        return this.player;
    }

    public void setPlayer(PlayerInfo value) {
        this.player = value;
    }
}
