
package cardbehavior;

/**
 * @author Michael
 */
public class CardBehaviorApp 
{   
    public static void main(String[] args) 
    {
        CardBase [] cards = new CardBase[] 
        {
            new FestivalCard()
        };
        
        for(CardBase card : cards)
        {
            card.performActions();
        }
    }    
}
