package dominion.results;

import dominion.model.*;

public class GameCardInfo {
    
    private int id;
    private String name;
    private String deck;
    private int count;
    private int cost;
    private boolean isAction;
    private boolean isCoin;
    private int value;

    public static GameCardInfo[] fromGame(Game game) {
        GameCard[] cards = game.getCards();
        GameCardInfo[] result = new GameCardInfo[cards.length];
        for(int i = 0; i < cards.length; i++) {
            result[i] = fromCard(cards[i]);
        }
        return result;
    }

    public static GameCardInfo fromCard(GameCard card) {
        GameCardInfo result = new GameCardInfo();
        result.setCost(card.getCost());
        result.setCount(card.getCount());
        result.setDeck(card.getDeck());
        result.setId(card.getId());
        result.setIsAction(card.getIsAction());
        result.setIsCoin(card.getIsCoin());
        result.setName(card.getName());
        result.setValue(card.getValue());
        return result;
    }
    
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDeck() {
        return this.deck;
    }

    public int getCount() {
        return this.count;
    }

    public int getCost() {
        return this.cost;
    }

    public boolean getIsAction() {
        return this.isAction;
    }

    public boolean getIsCoin() {
        return this.isCoin;
    }

    public int getValue() {
        return this.value;
    }

    public void setId(int value) {
        this.id = value;
    }

    public void setName(String value) {
        this.name = value;
    }

    public void setDeck(String value) {
        this.deck = value;
    }

    public void setCount(int value) {
        this.count = value;
    }

    public void setCost(int value) {
        this.cost = value;
    }

    public void setIsAction(boolean value) {
        this.isAction = value;
    }

    public void setIsCoin(boolean value) {
        this.isCoin = value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
