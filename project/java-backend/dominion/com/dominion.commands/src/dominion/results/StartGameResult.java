package dominion.results;

import dominion.model.*;
import dominion.routing.*;

public class StartGameResult extends ResultBase {

    private GameInfo game;
    private PlayerInfo[] players;
    private GameCardInfo[] gameCards;

    public StartGameResult() {
        super("startGame");
    }

    public StartGameResult(Game game) {
        this();
        this.setGame(GameInfo.fromGame(game));
        this.setGameCards(GameCardInfo.fromGame(game));
        this.setPlayers(PlayerInfo.fromGame(game));
    }

    public GameInfo getGame() {
        return this.game;
    }

    public PlayerInfo[] getPlayers() {
        return this.players;
    }

    public GameCardInfo[] getGameCards() {
        return this.gameCards;
    }

    public void setGame(GameInfo value) {
        this.game = value;
    }

    public void setPlayers(PlayerInfo[] value) {
        this.players = value;
    }

    public void setGameCards(GameCardInfo[] value) {
        this.gameCards = value;
    }

}
