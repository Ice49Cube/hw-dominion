/**
 * @author Michael
 */

package serializingtest;

public class StartNewGameResult extends ResultBase 
{
    public String name;
    public String[] players;

    public StartNewGameResult(StartNewGameCommand command, String name)
    {
        super(true, command);
        this.name = name;
        this.players = command.players;
    }
}
