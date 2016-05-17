package dominion.frontend.behaviors;

import dominion.commands.*;
import dominion.frontend.*;
import dominion.results.*;
import dominion.routing.*;

public class BuyBehavior implements IGameEngineBehavior {

    private BuyCardCommand buyCard(Game game, GameCardInfo card, PlayerInfo player) {
        BuyCardCommand cmd = new BuyCardCommand();
        cmd.setCancel(false);
        cmd.setCardId(card.getId());
        cmd.setGameId(game.getInfo().getId());
        cmd.setPlayerId(player.getId());
        return cmd;
    }

    private CommandBase buyCard(GameEngine engine, Game game, PlayerInfo player) throws Exception {
        while (true) {
            game.printAll();
            this.printBuys(player);
            String input = Console.readLine("> Enter a card name or C for cancel: ").trim();
            if (input.equalsIgnoreCase("C")) {
                return this.cancelBuy(game);
            }
            GameCardInfo[] cards = game.getCards(c -> c.getName().equalsIgnoreCase(input));
            if (cards.length == 1) {
                GameCardInfo card = cards[0];
                if (card.getCost() <= player.getCoins()) {
                    return buyCard(game, card, player);
                }
                System.out.println("> Not rich enough for this card! Press enter...");
            } else {
                System.out.println("> No such card! Press enter...");
            }
            Console.readLine();
        }
    }

    private CommandBase cancelBuy(Game game) {
        PlayerInfo player = game.getCurrentPlayer();
        System.out.println("> No coin cards, buys left or cancelled...");
        BuyCardCommand cmd = new BuyCardCommand();
        cmd.setGameId(game.getInfo().getId());
        cmd.setPlayerId(player.getId());
        cmd.setCancel(true);
        return cmd;
    }

    private void printBuys(PlayerInfo player) {
        int buys = player.getBuys();
        System.out.println(String.format("> You have %d buy%s and %d$.", buys, buys == 1 ? "" : "s", player.getCoins()));
    }
    
    @Override
    public CommandBase process(GameEngine engine, Game game) throws Exception {
        PlayerInfo player = game.getCurrentPlayer();
        if (player.getBuys() > 0) {
            return buyCard(engine, game, player);
        }
        return cancelBuy(game);
    }
}
