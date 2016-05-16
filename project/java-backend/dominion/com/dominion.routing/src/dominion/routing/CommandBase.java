package dominion.routing;

import com.fasterxml.jackson.annotation.*;

/**
 * Use the CommandBase class as 
 * base class for each command that comes from the client.
 */
@JsonIgnoreProperties(ignoreUnknown = false)
public class CommandBase
{
    @JsonProperty("_method_")
    private String method;    
    
    public CommandBase() {
        System.out.println("Pass the method to the constructor, todo: fix unit tests!");
    }
    
    public CommandBase(String method) {
        this.method = method;
    }
    
    public String getMethod()
    {
        return this.method;
    }
    
    public void setMethod(String value)
    {
        this.method = value;
    }
}
