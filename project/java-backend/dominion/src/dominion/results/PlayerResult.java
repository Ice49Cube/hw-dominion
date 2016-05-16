package dominion.results;

import dominion.model.Game;

public class PlayerResult {

	private int id;
	private String name;

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
	 * Note: The Game package doesn't know the command package. Plus it's a
	 * factory method.
	 */
	public static PlayerResult fromGame(Game game, dominion.model.Player source) {
		boolean isCurrent = source.getId() == game.getCurrentPlayerId();
		PlayerResult player = isCurrent ? new CurrentPlayerResult() : new PlayerResult();
		player.setId(source.getId());
		player.setName(source.getName());
		if (isCurrent) {
			CurrentPlayerResult current = (CurrentPlayerResult) player;
			current.setCards(source.getCards(
					c -> c.getPile().equals("Hand") || c.getPile().equals("Table") || c.getPile().equals("Playing")));
			current.setActions(source.getActions());
			current.setBuys(source.getBuys());
		}
		return player;
	}
}
