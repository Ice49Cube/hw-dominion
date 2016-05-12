package dominion.routing;


import dominion.routing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael
 */
public class ValidClass
{
    @RoutedCommand()
    public ResultBase validMethod(ValidCommand command)
    {
        System.out.println("method: " + command.getMethod());
        System.out.println("someString: " + command.someString);
        System.out.println("someBool: " + command.someBool);
        System.out.println("someNumber: " + command.someNumber);
        System.out.println("empty: " + command.empty);
        return new ValidResult(command.getMethod());
    }
    
    @RoutedCommand()
    public ResultBase validMethodWithComplexCommand(ValidComplexCommand command)
    {
        System.out.println("method: " + command.getMethod());
        System.out.println("user.name: " + command.user.name);
        if (!command.user.name.equals("Supercalifragilisticexpialidocious"))
            throw new java.lang.IllegalArgumentException();
        return new ValidResult(command.getMethod());
    }
}