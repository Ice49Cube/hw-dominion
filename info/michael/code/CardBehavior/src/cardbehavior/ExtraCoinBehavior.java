/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardbehavior;

/**
 *
 * @author Michael
 */
public class ExtraCoinBehavior implements ICardBehavior
{
    private final int howMany;
    
    public ExtraCoinBehavior(int howMany)
    {
        this.howMany = howMany;
    }
    
    @Override
    public void perform() 
    {
        System.out.println("\tExtra coins: " + this.howMany);
    }    
}
