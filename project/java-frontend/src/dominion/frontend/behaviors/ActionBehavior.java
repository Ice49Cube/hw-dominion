package dominion.frontend.behaviors;

import dominion.commands.PlayActionCommand;

import dominion.frontend.*;
import dominion.frontend.responses.*;

import dominion.routing.CommandBase;
import java.util.*;

public class ActionBehavior implements IGameEngineBehavior {

    private CommandBase cancelAction() {
        System.out.println("No action cards or actions left...");
        PlayActionCommand cmd = new PlayActionCommand();
        cmd.setCancel(true);
        return cmd;
    }
    
    private CommandBase playAction(GameEngine engine, Game game, Player player, ArrayList<PlayerCard> cards) {
        game.printGameCards();
        game.printPlayers();
        game.printCurrentPlayer();
        this.printActionCards(cards);
        engine.setBehavior(null);
        return null;
    }
    
    private void printActionCards(ArrayList<PlayerCard> cards){
        
    }

    @Override
    public CommandBase process(GameEngine engine, Game game) throws Exception {
        game.printGameCards();
        game.printPlayers();
        game.printCurrentPlayer();
        Player player = game.getCurrentPlayer();
        ArrayList<PlayerCard> cards = game.getCurrentPlayerActionCards();
        // Does the player have actions left and action cards?
        if (player.actions != 0 && !cards.isEmpty()) {
            return this.playAction(engine, game, player, cards);
        } else {
            return this.cancelAction();
        }
    }
}


/*

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

*/