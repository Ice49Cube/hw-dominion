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
        cmd.setGameId(game.getId());
        cmd.setPlayerId(player.getId());
        return cmd;
    }

    private CommandBase buyCard(GameEngine engine, Game game, PlayerInfo player, int amount) throws Exception {
        while (true) {
            game.printAll();
            System.out.println(String.format("> You have %d buys and %d$.", player.getBuys(), amount));
            System.out.print("> Enter a card name or C for cancel: ");
            String input = Console.readLine().trim();
            if (input.equalsIgnoreCase("C")) {
                return this.cancelBuy(game);
            }
            GameCardInfo[] cards = game.getCards(c -> c.getName().equalsIgnoreCase(input));
            if (cards.length == 1) {
                GameCardInfo card = cards[0];
                if (card.getCost() <= amount) {
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
        System.out.println("> No coin cards or buys left...");
        BuyCardCommand cmd = new BuyCardCommand();
        cmd.setGameId(game.getId());
        cmd.setPlayerId(game.getCurrentPlayer().getId());
        cmd.setCancel(true);
        return cmd;
    }

    @Override
    public CommandBase process(GameEngine engine, Game game) throws Exception {
        PlayerInfo player = game.getCurrentPlayer();
        if (player.getBuys() != 0) {
            PlayerCardInfo[] playerCards = player.getCards(c -> c.getPile().equals("Hand"));
            int total = 0;
            for (PlayerCardInfo playerCard : playerCards) {
                GameCardInfo card = game.getCard(playerCard.getCardId());
                if (card.getIsCoin()) {
                    total += card.getValue();
                }
            }
            return buyCard(engine, game, player, total);
        }
        return cancelBuy(game);
    }
}
