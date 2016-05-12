package dominion.frontend.behaviors;

import dominion.commands.StartGameCommand;
import dominion.frontend.Console;
import dominion.frontend.Game;
import dominion.frontend.GameEngine;

import dominion.routing.CommandBase;

import java.util.ArrayList;

public class StartGameBehavior implements IGameEngineBehavior {

    @Override
    public CommandBase process(GameEngine engine, Game game) throws Exception {
        String choice;
        while (true) {
            printMenu();
            choice = Console.readLine().trim();
            switch (choice) {
                case "q":
                case "Q":
                    engine.setBehavior(null);
                    return null;
                case "n":
                case "N":
                    return createStartNewGameCommand();
                default:
                    return createStartNewGameCommand(choice);
            }
        }
    }

    private CommandBase createStartNewGameCommand() throws Exception {
        String[] players = this.getPlayers();
        StartGameCommand command = new StartGameCommand();
        command.setMethod("startGame");
        command.setPlayerNames(players);
        command.setCardSet("ToDo Ask Card Set");
        return command;
    }

    private CommandBase createStartNewGameCommand(String choice) {
        StartGameCommand command = new StartGameCommand();
        command.setMethod("startGame");
        command.setCode(choice);
        return command;
    }

    private void printMenu() {
        System.out.println("New Game: N - Continue Game: code - Quit: Q");
        System.out.print("Your choice: ");
    }

    private static String[] getPlayers() throws Exception {
        ArrayList<String> players = new ArrayList();
        String player;
        while (players.size() < 4) {
            System.out.print("Enter name for player " + (players.size() + 1));
            if (players.size() > 1) {
                System.out.print(" or enter to start");
            }
            System.out.print(": ");
            player = Console.readLine().trim();
            if (player.equals("")) {
                if (players.size() > 1) {
                    break;
                }
            } else if (!players.contains(player)) {
                players.add(player);
            }
        }
        return players.toArray(new String[players.size()]);
    }
}
