package dominion.model.info;

import java.util.*;

/**
 * CardSet enum contains info to create a new game.
 */
public enum CardSet 
{	
    FirstGame("First Game", new CardInfo[] { 
        CardInfo.Cellar, CardInfo.Market, CardInfo.Militia, CardInfo.Mine, CardInfo.Moat, 
        CardInfo.Remodel, CardInfo.Smithy, CardInfo.Village, CardInfo.Woodcutter, CardInfo.Workshop 
    }),	
    BigMoney("Big Money", new CardInfo[] {
        CardInfo.Adventurer, CardInfo.Bureaucrat, CardInfo.Chancellor, CardInfo.Chapel, CardInfo.Feast,
        CardInfo.Laboratory, CardInfo.Market, CardInfo.Mine, CardInfo.Moneylender, CardInfo.ThroneRoom
    }),	
    Interaction("Interaction",  new CardInfo[] {
        CardInfo.Bureaucrat, CardInfo.Chancellor, CardInfo.CouncilRoom, CardInfo.Festival, CardInfo.Library, 
        CardInfo.Militia, CardInfo.Moat, CardInfo.Spy, CardInfo.Thief, CardInfo.Village
    }),	
    SizeDistortion("Size Distortion",  new CardInfo[] {
        CardInfo.Cellar, CardInfo.Chapel, CardInfo.Feast, CardInfo.Gardens, CardInfo.Laboratory,
        CardInfo.Thief, CardInfo.Village, CardInfo.Witch, CardInfo.Woodcutter, CardInfo.Workshop
    }),
    VillageSquare("Village Square",  new CardInfo[] {
        CardInfo.Bureaucrat, CardInfo.Cellar, CardInfo.Festival, CardInfo.Library, CardInfo.Market,
        CardInfo.Remodel, CardInfo.Smithy, CardInfo.ThroneRoom, CardInfo.Village, CardInfo.Woodcutter
    }),
    Random("Random", null)
    ;

    private final String name;
    private final CardInfo[] cards;

    CardSet(String name, CardInfo[] cards)
    {
        this.name  = name;
        this.cards = cards;
    }	

    public String getName()
    {
        return this.name;
    }

    public CardInfo[] getCards()
    {
        if(this.cards == null) 
            return getRandomCards();
        return this.cards;
    }

    public static CardSet getRandomCardSet()
    {
        Random random = new Random(new Date().getTime());
        CardSet[] sets = CardSet.values();
        return sets[random.nextInt(sets.length)];
    }

    private static CardInfo[] getRandomCards()
    {
        Random random = new Random(new Date().getTime());
        CardInfo[] cards = CardInfo.values();
        ArrayList<Integer> numbers = new ArrayList();   
        while (numbers.size() < 10) 
        {
            int value = random.nextInt(cards.length);
            if (!numbers.contains(value) && ((cards[value].getIsAction() || cards[value].equals(CardInfo.Gardens))))
                numbers.add(value);
        }		
        CardInfo[] result = new CardInfo[10];
        for(int i = 0; i < numbers.size(); i++)
                result[i] = cards[numbers.get(i)];
        return result;
    }
    
    public static CardSet parse(String name, CardSet defaultValue)
    {
        try
        {
            return CardSet.valueOf(name);
        }
        catch(Exception ex)
        {
            return defaultValue;
        }
    }
}
