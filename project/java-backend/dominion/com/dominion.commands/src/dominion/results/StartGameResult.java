package dominion.results;

import dominion.model.*;
import dominion.routing.*;

public class StartGameResult extends ResultBase {

    private GameInfo gameInfo;
    private PlayerInfo[] playerInfo;
    private GameCardInfo[] gameCardInfo;

    public StartGameResult() {
        super("startGame");
    }

    public StartGameResult(Game game) {
        this();
        this.setGameInfo(GameInfo.fromGame(game));
        this.setGameCardInfo(GameCardInfo.fromGame(game));
        this.setPlayerInfo(PlayerInfo.fromGame(game));
    }

    public GameInfo getGameInfo() {
        return this.gameInfo;
    }

    public PlayerInfo[] getPlayerInfo() {
        return this.playerInfo;
    }

    public GameCardInfo[] getGameCardInfo() {
        return this.gameCardInfo;
    }

    public void setGameInfo(GameInfo value) {
        this.gameInfo = value;
    }

    public void setPlayerInfo(PlayerInfo[] value) {
        this.playerInfo = value;
    }

    public void setGameCardInfo(GameCardInfo[] value) {
        this.gameCardInfo = value;
    }

}
