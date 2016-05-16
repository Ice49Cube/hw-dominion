package dominion.results;

import dominion.model.*;
import dominion.routing.*;

public class StartGameResult extends ResultBase {

	private int id;
	private PlayerResult[] players;
	private GameCard[] cards;
	private String cardSet;
	private String state;

	public StartGameResult() {
		super("startGame");
	}

	public StartGameResult(Game game) {
		this();
		this.id = game.getId();
		dominion.model.Player[] players = game.getPlayers();
		this.players = new PlayerResult[players.length];
		int index = 0;
		for (Player player : players) {
			this.players[index++] = PlayerResult.fromGame(game, player);
		}
		this.cards = game.getCards();
		this.cardSet = game.getCardSet();
		this.state = game.getState();
	}

	public int getId() {
		return this.id;
	}

	public PlayerResult[] getPlayers() {
		return this.players;
	}

	public GameCard[] getCards() {
		return this.cards;
	}

	public String getCardSet() {
		return this.cardSet;
	}

	public String getState() {
		return this.state;
	}
}