
package dominion.model.info;

// Doesn't have to be an enum, could come out a config file...
// Never played with this type of enums before. I know some teachers will cringe :)

// Name = name of the card, used in database
// Cost = the cost of the card
// Deck = the tpe of deck (a number in initializer becauce forward declaration problems do exist in java :) )
// CountBehavior = behavior that calculates the number of cards

/**
 * CardInfo enum contains info to create a new game.
 */
public enum CardInfo 
{	
	Gold("gold", false, true, 6, 3, TreasureCardsCountBehavior.BEHAVIOR),
	Silver("silver", false, true, 3, 2, TreasureCardsCountBehavior.BEHAVIOR),
	Copper("copper", false, true, 0, 1, TreasureCardsCountBehavior.BEHAVIOR),

	Adventurer("adventurer", true, false, 6, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Bureaucrat("bureaucrat", true, false, 4, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Cellar("cellar", true, false, 2, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Chancellor("chancellor", true, false, 3, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Chapel("chapel", true, false, 2, 0, KingdomCardsCountBehavior.BEHAVIOR),
	CouncilRoom("council_room", true, false, 5, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Feast("feast", true, false, 4, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Festival("festival", true, false, 5, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Gardens("gardens", false, false, 4, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Laboratory("laboratory", true, false, 5, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Library("library", true, false, 5, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Market("market", true, false, 5, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Militia("militia", true, false, 4, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Mine("mine", true, false, 5, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Moat("moat", true, false, 2, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Moneylender("moneylender", true, false, 4, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Remodel("remodel", true, false, 4, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Smithy("smithy", true, false, 4, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Spy("spy", true, false, 4, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Thief("thief", true, false, 4, 0, KingdomCardsCountBehavior.BEHAVIOR),
	ThroneRoom("throne_room", true, false, 4, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Village("village", true, false, 3, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Witch("witch", true, false, 5, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Woodcutter("woodcutter", true, false, 3, 0, KingdomCardsCountBehavior.BEHAVIOR),
	Workshop("workshop", true, false, 3, 0, KingdomCardsCountBehavior.BEHAVIOR),

	Province("province", false, false, 8, 0, VictoryCardsCountBehavior.BEHAVIOR),
	Duchy("duchy", false, false, 5, 0, VictoryCardsCountBehavior.BEHAVIOR),
	Estate("estate", false, false, 2, 0, VictoryCardsCountBehavior.BEHAVIOR),
	Curse("curse", false, false, 0, 0, CurseCardCountBehavior.BEHAVIOR)
	;
    
    private final String name;
    private final int cost;
    private final int value;
    private final boolean isAction;
    private final boolean isCoin;
    private final ICountBehavior countBehavior;
    
    CardInfo(String name, boolean action, boolean coin, int cost, int value, ICountBehavior countBehavior)
    {
        this.name = name;
        this.cost = cost;
        this.value = value;
        this.isAction = action;
        this.isCoin = coin;
        this.countBehavior = countBehavior;
    }
        
    public int getCost()
    {
    	return this.cost;
    }
    
    public int getCount(int numberOfPlayers)
    {
    	return this.countBehavior.calculate(this.name, numberOfPlayers);
    }

    public boolean getIsAction () {
    	return this.isAction;
    }
    
    public boolean getIsCoin () {
    	return this.isCoin;
    }
    
    public String getName()
    {
    	return this.name;
    }
    
    public int getValue() 
    {
    	return this.value;
    }
    
    public static CardInfo parse(String name) 
    {
        try
        {
            return CardInfo.valueOf(name);
        }
        catch(Exception e)
        {
            return CardInfo.valueOf(formatNameCasing(name));
        }
    }
    
    public static String formatNameCasing(String name)
    {
    	String[] parts = name.split("_");
    	for(int i = 0; i < parts.length; i++)
            parts[i] = parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1);
    	return String.join("",  parts);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Private implementations
    
    private interface ICountBehavior
    {
    	int calculate(String name, int numberOfPlayers);
    }

    private static class KingdomCardsCountBehavior implements ICountBehavior 
    {
    	protected KingdomCardsCountBehavior(){};
    	
    	public static final ICountBehavior BEHAVIOR = new KingdomCardsCountBehavior();

    	@Override
    	public int calculate(String name, int numberOfPlayers) 
    	{
            return 10;
    	}
    }
    
    private static class TreasureCardsCountBehavior implements ICountBehavior 
    {
    	protected TreasureCardsCountBehavior (){};
    	
    	public static final ICountBehavior BEHAVIOR = new TreasureCardsCountBehavior();

    	@Override
    	public int calculate(String name, int numberOfPlayers) 
    	{
            if (name.equals("gold")) {
                return 30;
            } else if (name.equals("silver")) {
                return 40;
            } else if(name.equals("copper")) {
                return 60 - (numberOfPlayers * 7);
            } else throw new IllegalArgumentException();
    	}
    }

    private static class VictoryCardsCountBehavior implements ICountBehavior 
    {
    	protected VictoryCardsCountBehavior (){};
    	
    	public static final ICountBehavior BEHAVIOR = new VictoryCardsCountBehavior ();

    	@Override
    	public int calculate(String name, int numberOfPlayers) 
    	{
            return numberOfPlayers == 2 ? 8 : 12;
    	}
    }

    private static class CurseCardCountBehavior implements ICountBehavior 
    {
    	protected CurseCardCountBehavior (){};
    	
    	public static final ICountBehavior BEHAVIOR = new CurseCardCountBehavior ();

    	@Override
    	public int calculate(String name, int numberOfPlayers) 
    	{
            return (numberOfPlayers - 1) * 10;
    	}
    }
}