/**
 * @author Michael
 */

package serializingtest;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommandBase
{
    public String method;
}