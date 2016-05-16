package dominion.frontend.responses;

public class GameCard {

    private int id;
    private String name;
    private String deck;
    private int count;
    private int cost;
    private boolean isAction;
    private boolean isCoin;
    private int value;

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

/* 		{
			"id": 647,
			"count": 30,
			"cost": 6,
			"deck": "Treasure",
			"isAction": false,
			"isCoin": true,
			"name": "gold",
			"value": 3
		},
 */
