package dominion.frontend.responses;

public class PlayerCard {

    private int id;
    private String pile;
    private int order;
    private int cardId;

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
