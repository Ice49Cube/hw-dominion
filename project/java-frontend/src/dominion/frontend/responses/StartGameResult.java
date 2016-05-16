package dominion.frontend.responses;

import dominion.routing.ResultBase;

public class StartGameResult extends ResultBase {

    private int id;
    private GameCard[] cards;
    private Player[] players;
    private String cardSet;
    private String state;

    public StartGameResult() {
        super("startGame");
    }

    public int getId() {
        return this.id;
    }

    public GameCard[] getCards() {
        return this.cards;
    }

    public Player[] getPlayers() {
        return this.players;
    }

    public String getCardSet() {
        return this.cardSet;
    }

    public String getState() {
        return this.state;
    }

    public void setId(int value) {
        this.id = value;
    }

    public void setCards(GameCard[] value) {
        this.cards = value;
    }

    public void setPlayers(Player[] value) {
        this.players = value;
    }

    public void setCardSet(String value) {
        this.cardSet = value;
    }

    public void setState(String value) {
        this.state = value;
    }

}
