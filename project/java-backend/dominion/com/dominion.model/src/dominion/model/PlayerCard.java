package dominion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PlayerCard 
{
    @JsonIgnore()
    private final Player player;
    private final int cardId;
    private final int id;
    private String pile;
    private int order;
    
    public PlayerCard(Player player, int id, int cardId, String pile, int order)
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
}
