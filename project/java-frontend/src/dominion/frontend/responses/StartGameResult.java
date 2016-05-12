package dominion.frontend.responses;

import dominion.routing.ResultBase;

public class StartGameResult extends ResultBase {

    public int id;
    public GameCard[] cards;
    public Player[] players;

    public StartGameResult() {
        super("startGame");
    }
}
