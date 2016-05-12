package dominion.game.cards;

import java.util.HashMap;

public class CardFactory {

	private final HashMap<String, ICard> cards;

	public CardFactory() {
		this.cards = new HashMap();
	}

	public ICard load(String name) {
		return null;
	}
}
