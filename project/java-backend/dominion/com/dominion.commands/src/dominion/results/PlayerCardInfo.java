package dominion.results;

import dominion.model.*;

public class PlayerCardInfo {

    private int id;
    private String pile;
    private int order;
    private int cardId;

    public static PlayerCardInfo[] fromCards(Player player, PlayerCard[] cards) {
        PlayerCardInfo[] result = new PlayerCardInfo[cards.length];
        for(int i = 0; i < cards.length; i++) {
            result[i] = fromCard(cards[i]);
        }
        return result;
    }
    
    public static PlayerCardInfo fromCard(PlayerCard card) {
        PlayerCardInfo result = new PlayerCardInfo();
        result.setCardId(card.getCardId());
        result.setId(card.getId());
        result.setOrder(card.getOrder());
        result.setPile(card.getPile());
        return result;
    }
    
    public int getId() {
        return this.id;
    }

    public String getPile() {
        return this.pile;
    }

    public int getOrder() {
        return this.order;
    }

    public int getCardId() {
        return this.cardId;
    }

    public void setId(int value) {
        this.id = value;
    }

    public void setPile(String value) {
        this.pile = value;
    }

    public void setOrder(int value) {
        this.order = value;
    }

    public void setCardId(int value) {
        this.cardId = value;
    }

}
