package dominion.model;

import dominion.model.database.*;
import java.sql.*;

public class PlayerCard2 {

    private final Player2 player;
    private final int cardId;
    private final int id;
    private String pile;
    private int order;

    private PlayerCard2(Player2 player, int id, int cardId, String pile, int order) {
        this.id = id;
        this.player = player;
        this.cardId = cardId;
        this.pile = pile;
        this.order = order;
    }

    static PlayerCard2 create(Connection con, Player2 player, int gameCardId, String pile, int order) throws Exception {
        String sql = "INSERT INTO playercards (player, card, pile, `order`) VALUES(?, ?, ?, ?)";
        Object[] args = new Object[]{player.getId(), gameCardId, pile, order};
        int recordId = Database.executeInsert(con, sql, args);
        return new PlayerCard2(player, recordId, gameCardId, pile, order);
    }
    
    static PlayerCard2 load(Player2 player, ResultSet rs) throws Exception {
        return new PlayerCard2(player, rs.getInt("playercards.id"), rs.getInt("playercards.card"), rs.getString("playercards.pile"), rs.getInt("playercards.order"));
    }
    
    public int getCardId() {
        return this.cardId;
    }

    public int getId() {
        return this.id;
    }

    public int getOrder() {
        return this.order;
    }

    public String getPile() {
        return this.pile;
    }

    public Player2 getPlayer() {
        return this.player;
    }

    void setOrder(int value) {
        this.order = value;
    }

    void setPile(String value) {
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
