package dominion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GameCard 
{
    @JsonIgnore()
    private final Game game;

    private final int id;
    private final String name;
    private final String deck;
    private final int count;
    private final int cost;
    private final int value;
    private final boolean isAction;
    private final boolean isCoin;
    
    public GameCard(int id, Game game, String name, String deck, int count, int cost, int value, boolean isAction, boolean isCoin)
    {
        this.id = id;
        this.game = game;
        this.name = name;
        this.deck = deck;
        this.count = count;
        this.cost = cost;
        this.value = value;
        this.isAction = isAction;
        this.isCoin = isCoin;
    }
    
    public boolean getIsAction() {
        return this.isAction;
    }
    
    public boolean getIsCoin() {
        return this.isCoin;
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

    public String getName()
    {
        return this.name;
    }
    
    public int getValue()
    {
        return this.value;
    }
    /*public void setCount(int value)
    {
        this.count = value;
    }*/
}
