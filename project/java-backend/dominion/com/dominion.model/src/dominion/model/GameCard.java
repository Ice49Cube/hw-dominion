package dominion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GameCard 
{
    private final int id;
    private final int count;
    private final int cost;
    private final String deck;
	@JsonIgnore()
    private final Game game;
    private final boolean isAction;
    private final boolean isCoin;
    private final String name;
    private final int value;
    
    public GameCard(Game game, int id, String name, String deck, int count, int cost, int value, boolean isAction, boolean isCoin)
    {
        this.game = game;
        this.id = id;
        this.name = name;
        this.deck = deck;
        this.count = count;
        this.cost = cost;
        this.value = value;
        this.isAction = isAction;
        this.isCoin = isCoin;
    }
    
    public int getCount()
    {
        return this.count;
    }
    
    public int getCost()
    {
    	return this.cost;
    }
    
    public String getDeck()
    {
        return this.deck;
    }
    
    public Game getGame()
    {
        return this.game;
    }

    public int getId()
    {
        return this.id;
    }

    public boolean getIsAction() {
        return this.isAction;
    }
    
    public boolean getIsCoin() {
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
}
