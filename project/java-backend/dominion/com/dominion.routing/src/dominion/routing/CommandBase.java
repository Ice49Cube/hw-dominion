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
    
    public String getMethod()
    {
        return this.method;
    }
    
    public void setMethod(String value)
    {
        this.method = value;
    }
}
