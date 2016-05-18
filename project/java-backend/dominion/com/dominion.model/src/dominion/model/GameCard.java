package dominion.model;

import dominion.model.database.*;
import java.sql.*;

public class GameCard 
{
    private final int id;
    private final int cost;
    private final String deck;
    private final Game game;
    private final boolean isAction;
    private final boolean isCoin;
    private final String name;
    private final int value;
    private int count;
    
    GameCard(Game game, int id, String name, String deck, int count, int cost, int value, boolean isAction, boolean isCoin)
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
    
    void setCount(int value) {
        this.count = value;
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
    
    void updateCount(Connection con, int value) throws Exception {
        String sql = "UPDATE gamecards SET count = ? WHERE id = ?";
        Object[] args = new Object[]{value, this.id};
        int affected = Database.executeUpdate(con, sql, args);
        Guard.validateAffected(1, affected, "GameCard.updateCount");
        this.count = value;
    }
}
