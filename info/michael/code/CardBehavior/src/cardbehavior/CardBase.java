
package cardbehavior;

/**
 *
 * @author Michael
 */
public abstract class CardBase {
    
    private ICardBehavior[] behaviors;
    private final String name;
    
    public CardBase(String name)
    {
        this.name = name;
    }
    
    protected ICardBehavior[] getBehavior()
    {
        return this.behaviors;
    }
    
    protected void setBehavior(ICardBehavior[] behaviors)
    {
        this.behaviors = behaviors;
    }
    
    public void performActions()
    {
        System.out.println("Card " + this.name);
        for(ICardBehavior behavior : this.behaviors)
            behavior.perform();
        System.out.println();
    }
}
