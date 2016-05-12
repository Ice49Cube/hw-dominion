/**
 * @author Michael
 */

package serializingtest;

import java.lang.annotation.*;

/**
 * The CommandName annotation. Each routed function should be annotated
 * with the CommandName annotation and set the name attribute it's value
 * to the typename of the function's argument.
 */
@Retention(RetentionPolicy.RUNTIME)
@interface CommandName 
{
    /**
     * The typename of the argument.
     * @return The typename of the argument.
     */
    public String name() ;
}
