package dominion.results;

import dominion.model.*;
import dominion.routing.ResultBase;

public class StartBetResult extends ResultBase {

    public StartBetResult() {
        super("startBet");
    }

    public StartBetResult(Game game) {
        this();
    }


}
