package dominion.frontend.responses;

import dominion.routing.ResultBase;

public class StartGameResult extends ResultBase {

    public int id;
    public GameCard[] cards;
    public Player[] players;
    public String cardSet;
    public String state;

    public StartGameResult() {
        super("startGame");
    }
}
