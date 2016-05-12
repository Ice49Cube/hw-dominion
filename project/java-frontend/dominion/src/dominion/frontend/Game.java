
package dominion.frontend;

import dominion.game.commands.StartGameCommand;
import dominion.game.commands.StartGameResult;
import dominion.routing.CommandBase;

public class Game 
{
    private final Routing routing;
    private final String[] players;
    private int currentPlayer;
    
    private Game(Routing routing, StartGameResult result)
    {
        this.routing = routing;
        this.players = result.players;
    }

    public static Game startGame(Routing routing, String code) throws Exception
    {
        StartGameCommand command = new StartGameCommand();
        command.code = code;
        return startGame(routing, command);
    }
    
    public static Game startGame(Routing routing, String[] playerNames) throws Exception
    {
        StartGameCommand command = new StartGameCommand();
        command.playerNames = playerNames;
        return startGame(routing, command);
    }    

    private static Game startGame(Routing routing, CommandBase command) throws Exception
    {
        StartGameResult result = (StartGameResult)routing.invoke("startGame", command);
        return new Game(routing, result);
    }
    
    public void run()
    {
        
    }
}
