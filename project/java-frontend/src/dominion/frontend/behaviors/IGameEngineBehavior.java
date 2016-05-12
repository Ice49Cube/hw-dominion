package dominion.frontend.behaviors;

import dominion.frontend.Game;
import dominion.frontend.GameEngine;
import dominion.routing.CommandBase;

/**
 * CommandLine input behavior.
 */
public interface IGameEngineBehavior {

    /**
     * Process the input for the current state.
     *
     * @param engine
     * @param game
     * @return The command for the server.
     * @throws Exception
     */
    CommandBase process(GameEngine engine, Game game) throws Exception;
}
