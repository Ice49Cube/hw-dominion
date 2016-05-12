/**
 * @author Michael
 */
package serializingtest;

public abstract class ResultBase 
{
    public boolean success;
    public String method;
    
    public ResultBase(boolean success, String method)
    {
        this.success = success;
        this.method = method;
    }
    
    public ResultBase(boolean success, CommandBase command)
    {
        this(success, command.method);
    }
}
