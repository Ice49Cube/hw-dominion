/**
 * @author Michael
 */

package serializingtest;

public class Game
{
    @CommandName(name = "serializingtest.StartNewGameCommand")
    public ResultBase startNewGame(StartNewGameCommand command)
    {
        StartNewGameResult result = new StartNewGameResult(command, "COOL");
        return result;
    }
}