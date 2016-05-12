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
public class FestivalCard extends CardBase
{
    public FestivalCard()
    {
        super("Festival");
        this.setBehavior(new ICardBehavior[]{
            new ExtraActionBehavior(2),
            new BuyCardBehavior(1),
            new ExtraCoinBehavior(2)
        });
    }
}
