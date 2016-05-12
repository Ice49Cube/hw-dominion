package dominion.routing;


import java.lang.annotation.*;

/**
 * The RoutedCommand annotation marks a method to be used for routing. 
 * The method should have one argument that has CommandBase as base type and
 * return a result that has ResultBase as base type.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RoutedCommand 
{

}