package dominion.frontend.behaviors;

import dominion.frontend.*;
import dominion.routing.*;
import dominion.commands.*;
import dominion.results.*;
import java.util.Arrays;
import java.util.List;

/**
 * Implements the behavior where the user uses treasures before the first buy.
 * In the online game it's possible to select those cards, here there was not
 * enough time and there's an auto bet all.
 */
public class BetBehavior implements IGameEngineBehavior {

    @Override()
    public CommandBase process(GameEngine engine, Game game) throws Exception {
        game.printAll();
        PlayerInfo player = game.getCurrentPlayer();
        List<PlayerCardInfo> coins = Arrays.asList(player.getCards(c -> c.getPile().equals("Hand") && game.getCard(c.getCardId()).getIsCoin()));
        BetCoinsCommand cmd = new BetCoinsCommand();
        cmd.setGameId(game.getInfo().getId());
        cmd.setPlayerId(player.getId());
        if (coins.isEmpty()) {
            System.out.println("> Bet cancel...");
            cmd.setCancel(true);
        } else {
            System.out.println("> Auto bet all...");
            cmd.setCoinCards(coins.stream().map(PlayerCardInfo::getId).mapToInt(Integer::intValue).toArray());
        }
        return cmd;
    }
}
