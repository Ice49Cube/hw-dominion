
package serializingtest;

/**
 * @author Michael
 */
public class ErrorResult extends ResultBase
{
    public String exception;
    public String information;
    
    public ErrorResult(String method, String exception, String information)
    {
        super(false, method);
        this.exception = exception;
        this.information = information;
    }
}
