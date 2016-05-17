package dominion.results;

import dominion.model.*;
import java.util.HashMap;
import java.util.function.Predicate;

public class PlayerInfo {

    private int id;
    private String name;
    private HashMap<Integer, PlayerCardInfo> cards;
    private int actions;
    private int buys;
    private int coins;

    public void addCard(PlayerCardInfo card) {
        this.cards.put(card.getId(), card);
    }
    
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(int value) {
        this.id = value;
    }

    public void setName(String value) {
        this.name = value;
    }

    /*
     * In the future pass extra arguments to reveal the correct info of the 
     * specified player.
     */
    public static PlayerInfo[] fromGame(Game game) {
        Player[] players = game.getPlayers();
        PlayerInfo[] result = new PlayerInfo[players.length];
        int index = 0;
        for (Player player : players) {
            result[index++] = PlayerInfo.fromPlayer(game, player);
        }
        return result;
    }

    public static PlayerInfo fromPlayer(Game game, Player source) {
        PlayerInfo result = new PlayerInfo();
        result.setId(source.getId());
        result.setName(source.getName());
        if (source.getIsCurrent()) {
            result.setActions(source.getActions());
            result.setBuys(source.getBuys());
            result.setCoins(source.getCoins());
            PlayerCard[] cards = source.getCards(
                    c -> c.getPile().equals("Hand") || c.getPile().equals("Table") || c.getPile().equals("Playing"));
            result.setCards(PlayerCardInfo.fromCards(source, cards));
        }
        return result;
    }

    public PlayerCardInfo[] getCards(Predicate<? super PlayerCardInfo> filter) {
        return this.cards == null ? null : this.cards.values().stream().filter(filter).toArray(size -> new PlayerCardInfo[size]);
    }

    public PlayerCardInfo[] getCards() {
        return this.cards == null ? null : this.cards.values().toArray(new PlayerCardInfo[this.cards.size()]);
    }

    public PlayerCardInfo getCard(int id) {
        return this.cards.get(id);
    }
    
    public void setCards(PlayerCardInfo[] value) {
        if (value != null) {
            this.cards = new HashMap<>();
            for (int i = 0; i < value.length; i++) {
                this.cards.put(value[i].getId(), value[i]);
            }
        } else {
            this.cards = null;
        }
    }

    public int getActions() {
        return this.actions;
    }

    public int getBuys() {
        return this.buys;
    }

    public int getCoins() {
        return this.coins;
    }

    public void setActions(int value) {
        this.actions = value;
    }

    public void setBuys(int value) {
        this.buys = value;
    }

    public void setCoins(int value) {
        this.coins = value;
    }

}
