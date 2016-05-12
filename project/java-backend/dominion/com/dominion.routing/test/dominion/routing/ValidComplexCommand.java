package dominion.routing;


import com.fasterxml.jackson.annotation.*;
import dominion.routing.CommandBase;

/**
 *
 * @author Michael
 */
public class ValidComplexCommand extends CommandBase
{
    @JsonProperty("user")
    public User user;
}
