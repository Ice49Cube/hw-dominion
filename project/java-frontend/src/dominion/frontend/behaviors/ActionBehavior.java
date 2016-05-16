package dominion.frontend.behaviors;

import dominion.commands.PlayActionCommand;

import dominion.frontend.*;
import dominion.frontend.responses.*;

import dominion.routing.CommandBase;
import java.util.*;

public class ActionBehavior implements IGameEngineBehavior {

    private CommandBase cancelAction(Game game) {
        System.out.println("> No action cards or actions left...");
        PlayActionCommand cmd = new PlayActionCommand();
        cmd.setGameId(game.getId());
        cmd.setPlayerId(game.getCurrentPlayer().getId());
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

    private void printActionCards(ArrayList<PlayerCard> cards) {

    }

    @Override
    public CommandBase process(GameEngine engine, Game game) throws Exception {
        game.printGameCards();
        game.printPlayers();
        game.printCurrentPlayer();
        Player player = game.getCurrentPlayer();
        // Does the player have actions left and action cards?
        if (player.getActions() != 0) {
            ArrayList<PlayerCard> cards = game.getCurrentPlayerActionCards();
            if (!cards.isEmpty()) {
                return this.playAction(engine, game, player, cards);
            }
        } 
        return this.cancelAction(game);        
    }
}


