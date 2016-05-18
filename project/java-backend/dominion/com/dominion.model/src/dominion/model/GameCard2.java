package dominion.model;

import dominion.model.database.*;
import dominion.model.info.*;
import java.sql.*;

public class GameCard2 {
    
    private final Game2 game;
    private final int id;
    private final String deck;
    private final CardInfo info;
    private int count;
    
    private GameCard2(Game2 game, int id, String deck, int count, CardInfo info)
    {
        this.game = game;
        this.id = id;
        this.deck = deck;
        this.count = count;
        this.info = info;
    }
    
    static GameCard2 create(Connection con, Game2 game, String deck, CardInfo info, int numberOfPlayers) throws Exception {
        String sql = "INSERT INTO gamecards (game, name, deck, `count`, cost, value, isaction, iscoin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int count = info.getCount(numberOfPlayers);
        Object[] args = new Object[] {
            game.getId(), 
            info.getName(), 
            deck, 
            count, 
            info.getCost(), 
            info.getValue(), 
            info.getIsAction() ? 1 : 0, 
            info.getIsCoin() ? 1 : 0
        };
        int recordId = Database.executeInsert(con, sql, args);
        return new GameCard2(game, recordId, deck, count, info);
    }

    static GameCard2 load(Game2 game, ResultSet rs) throws Exception {
        return new GameCard2(game, rs.getInt("gamecards.id"), rs.getString("gamecards.deck"), rs.getInt("gamecards.count"), CardInfo.parse(rs.getString("gamecards.name")));
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
    	return this.info.getCost();
    }
    
    public String getDeck()
    {
        return this.deck;
    }
    
    public Game2 getGame()
    {
        return this.game;
    }

    public int getId()
    {
        return this.id;
    }

    public boolean getIsAction() {
        return this.info.getIsAction();
    }
    
    public boolean getIsCoin() {
        return this.info.getIsCoin();
    }

    public String getName()
    {
        return this.info.getName();
    }
    
    public int getValue()
    {
        return this.info.getValue();
    }
    
    void updateCount(Connection con, int value) throws Exception {
        String sql = "UPDATE gamecards SET count = ? WHERE id = ?";
        Object[] args = new Object[]{value, this.id};
        int affected = Database.executeUpdate(con, sql, args);
        Guard.validateAffected(1, affected, "GameCard.updateCount");
        this.count = value;
    }
    
}
