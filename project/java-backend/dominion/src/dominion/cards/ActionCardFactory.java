package dominion.cards;

import java.util.HashMap;

public class ActionCardFactory {

	private final HashMap<String, IActionCard> cards;

	public ActionCardFactory() {
		this.cards = new HashMap<String, IActionCard>();
	}

	public IActionCard load(String name) {
		return this.cards.get(name);
	}
}
