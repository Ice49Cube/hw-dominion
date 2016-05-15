package dominion.game.cards;

import dominion.commands.PlayActionCommand;
import dominion.model.*;
import dominion.routing.*;

public interface IActionCard {
	
	ResultBase execute(Game game, PlayActionCommand command);

}
