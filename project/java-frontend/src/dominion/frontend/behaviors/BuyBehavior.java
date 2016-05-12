package dominion.frontend.behaviors;

import dominion.frontend.Game;
import dominion.frontend.GameEngine;
import dominion.routing.CommandBase;

public class BuyBehavior implements IGameEngineBehavior {

    @Override
    public CommandBase process(GameEngine engine, Game game) {
        // Todo: commandline input here to let current player buy cards
        // Todo: return the next command for the server
        return null;
    }

}
