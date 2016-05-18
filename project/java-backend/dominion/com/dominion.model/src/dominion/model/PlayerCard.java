package dominion.model;

import dominion.model.database.Database;
import java.sql.Connection;

public class PlayerCard 
{
    private final Player player;
    private final int cardId;
    private final int id;
    private String pile;
    private int order;
    
    PlayerCard(Player player, int id, int cardId, String pile, int order)
    {
        this.id = id;
        this.player = player;
        this.cardId = cardId;
        this.pile = pile;
        this.order = order;
    }    

    public int getCardId()
    {
        return this.cardId;
    }
    
    public int getId()
    {
        return this.id;
    }

    public int getOrder()
    {
        return this.order;
    }

    public String getPile()
    {
        return this.pile;
    }

    public Player getPlayer()
    {
        return this.player;
    }
    
    void setOrder(int value)
    {
        this.order = value;
    }

    void setPile(String value)
    {
        this.pile = value;
    }
    
    //\"Table\"
    void updatePileAndOrder(Connection con, String pile, int order) throws Exception {
        String sql = "UPDATE playercards SET pile = ?, `order` = ? WHERE id = ?";
        Object[] args = new Object[]{pile, order, this.getId()};
        int affected = Database.executeUpdate(con, sql, args);
        Guard.validateAffected(1, affected, "PlayerCard.updatePileAndOrder");
        this.pile = pile;
        this.order = order;
    }
}
