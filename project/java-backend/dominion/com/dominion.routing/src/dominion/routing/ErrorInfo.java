
package dominion.routing;

//import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Error info class is used as a base class to return error info. 
 * Note: Extra properties are add onto this class 
 *       by using anonymous inheritance. See: ErrorResult.java
 */
public class ErrorInfo 
{
    private final String message;
        
    public ErrorInfo(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return this.message;
    }
}
