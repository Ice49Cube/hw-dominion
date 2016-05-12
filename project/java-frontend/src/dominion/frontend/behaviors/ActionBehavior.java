package dominion.frontend.behaviors;

import dominion.commands.TestServerCommand;
import dominion.frontend.Console;
import dominion.frontend.Game;
import dominion.frontend.GameEngine;
import dominion.routing.CommandBase;

public class ActionBehavior implements IGameEngineBehavior {

    @Override
    public CommandBase process(GameEngine engine, Game game) throws Exception {
        game.printGameCards();
        // Todo: commandline input here to let current player play actions
        // Todo: return the next command for the server
        // User has no action cards -> send cancel buys to server...
        // Return card to play or cancel to server...
        String s = "";
        while (!s.equals("t") && !s.equals("q")) {
            System.out.print("Test server(t) or quit(q): ");
            s = Console.readLine().trim().toLowerCase();
        }
        if (s.equals("t")) {
            TestServerCommand command = new TestServerCommand();
            command.setMethod("testServer");
            command.setCode(200);
            command.setSuccess(true);
            return command;
        } else {
            // Exit...
            engine.setBehavior(null);
            return null;
        }
    }

}
