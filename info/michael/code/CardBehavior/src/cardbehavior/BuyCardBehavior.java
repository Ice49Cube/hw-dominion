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
public class BuyCardBehavior implements ICardBehavior
{
    private final int howMany;
    
    public BuyCardBehavior(int howMany)
    {
        this.howMany = howMany;
    }

    @Override
    public void perform() 
    {
        System.out.println("\tCan buy extra cards: " + this.howMany);
    }   
}
