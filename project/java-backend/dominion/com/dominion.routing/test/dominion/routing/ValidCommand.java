package dominion.routing;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dominion.routing.CommandBase;
import com.fasterxml.jackson.annotation.*;

/**
 *
 * @author Michael
 */
public class ValidCommand extends CommandBase
{
    @JsonProperty( )
    public String someString;
    @JsonProperty("someBool")
    public boolean someBool;
    @JsonProperty("empty")
    public Object empty;
    @JsonProperty("someNumber")
    public double someNumber;
}
