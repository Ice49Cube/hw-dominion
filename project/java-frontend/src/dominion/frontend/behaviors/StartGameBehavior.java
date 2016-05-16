package dominion.frontend.behaviors;

import dominion.commands.StartGameCommand;
import dominion.frontend.Console;
import dominion.frontend.Game;
import dominion.frontend.GameEngine;

import dominion.routing.CommandBase;

import java.util.ArrayList;
import java.util.Arrays;

public class StartGameBehavior implements IGameEngineBehavior {

    @Override
    public CommandBase process(GameEngine engine, Game game) throws Exception {
        String choice;
        while (true) {
            System.out.println("> New Game: N - Continue Game: code - Quit: Q");
            System.out.print("> Your choice: ");
            choice = Console.readLine().trim();
            switch (choice) {
                case "q":
                case "Q":
                    return engine.quit();
                case "n":
                case "N":
                    return createStartNewGameCommand();
                default:
                    return createStartNewGameCommand(choice);
            }
        }
    }

    private CommandBase createStartNewGameCommand() throws Exception {
        String[] players = this.readPlayers();
        StartGameCommand command = new StartGameCommand();
        command.setPlayerNames(players);
        command.setCardSet(this.readCardSet());
        return command;
    }

    private CommandBase createStartNewGameCommand(String choice) {
        StartGameCommand command = new StartGameCommand();
        command.setCode(choice);
        return command;
    }

    private String[] readPlayers() throws Exception {
        ArrayList<String> players = new ArrayList();
        String player;
        while (players.size() < 4) {
            System.out.print("> Enter name for player " + (players.size() + 1));
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
    
    private String readCardSet() throws Exception {
        ArrayList<String> cardSets = new ArrayList(Arrays.asList(new String[]{"First Game", "Big Money", "Interaction", "Size Distortion", "Village Square", "Random"}));
        while(true) {
            System.out.println("> Available card sets: " + String.join(", ", cardSets.toArray(new String[cardSets.size()])));
            System.out.print("> Please enter a cardset: ");
            String cardSet = Console.readLine().trim();
            if (cardSets.contains(cardSet)) 
                return cardSet.replace(" ", "");
        }
    }
}