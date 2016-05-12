package dominion.commands;

import dominion.model.*;
import dominion.routing.*;

public class StartGameResult extends ResultBase {

    private int id;
    private Player[] players;
    private GameCard[] cards;
    private String cardSet;
    
    public StartGameResult(Game game) {
        super("startGame");
        this.id = game.getId();
        dominion.model.Player[] players = game.getPlayers();
        this.players = new Player[players.length];
        int index = 0;
        for (dominion.model.Player player : players) {
            this.players[index++] = Player.fromGame(game, player);
        }
        this.cards = game.getCards();
        this.cardSet = game.getCardSet();
    }

    public int getId() {
        return this.id;
    }

    public Player[] getPlayers() {
        return this.players;
    }

    public GameCard[] getCards() {
        return this.cards;
    }
    
    public String getCardSet() {
    	return this.cardSet;
    }
}
