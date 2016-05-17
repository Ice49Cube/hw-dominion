package dominion.frontend.behaviors;

import dominion.commands.PlayActionCommand;

import dominion.frontend.*;
import dominion.results.*;

import dominion.routing.CommandBase;
import java.util.*;

public class ActionBehavior implements IGameEngineBehavior {

    private CommandBase cancelAction(Game game) {
        System.out.println("> No action cards or actions left...");
        PlayActionCommand cmd = new PlayActionCommand();
        cmd.setGameId(game.getInfo().getId());
        cmd.setPlayerId(game.getCurrentPlayer().getId());
        cmd.setCancel(true);
        return cmd;
    }

    private CommandBase playAction(GameEngine engine, Game game, PlayerInfo player, ArrayList<PlayerCardInfo> cards) {
        this.printActionCards(cards);
        return engine.quit();
    }

    private void printActionCards(ArrayList<PlayerCardInfo> cards) {

    }

    @Override
    public CommandBase process(GameEngine engine, Game game) throws Exception {
        game.printAll();
        PlayerInfo player = game.getCurrentPlayer();
        // Does the player have actions left and action cards?
        if (player.getActions() != 0) {
            ArrayList<PlayerCardInfo> cards = game.getCurrentPlayerActionCards();
            if (!cards.isEmpty()) {
                return this.playAction(engine, game, player, cards);
            }
        } 
        return this.cancelAction(game);        
    }
}


