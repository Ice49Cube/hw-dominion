package dominion.frontend;

import dominion.results.*;

import dominion.frontend.behaviors.*;
//import dominion.frontend.responses.*;

import dominion.routing.CommandBase;
import dominion.routing.ResultBase;

import java.lang.reflect.Method;
import java.util.HashMap;

public class GameEngine {

    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Constructors and member declarations" defaultstate="collapsed">
    private final Routing routing;

    private final HashMap<String, IGameEngineBehavior> behaviors;
    private Game game;
    private IGameEngineBehavior behavior;
    private ResultBase response;

    public GameEngine(String url) {
        this.routing = new Routing(url, this);
        this.behavior = new StartGameBehavior();
        this.behaviors = new HashMap<>();
        this.behaviors.put("Action", new ActionBehavior());
        this.behaviors.put("Buy", new BuyBehavior());
        this.behaviors.put("Bet", new BetBehavior());

    }

    // </editor-fold>
    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Dispatching methods" defaultstate="collapsed">
    public void run() throws Exception {
        while (this.behavior != null) {
            this.step();
        }
    }

    public void step() throws Exception {
        CommandBase command = this.behavior.process(this, this.game);
        if (command != null) {
            this.response = routing.invoke(command);
            Method method = this.routing.getMethod(this.response.getMethod());
            method.invoke(this, this.response);
        }
    }
    // </editor-fold>
    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Accessors and methods">

    public IGameEngineBehavior getBehavior() {
        return this.behavior;
    }

    public CommandBase quit() {
        this.setBehavior(null);
        return null;
    }

    public void setBehavior(IGameEngineBehavior value) {
        this.behavior = value;
    }

    private void setStateAndBehavior(String state) {
        this.game.getInfo().setState(state);
        this.behavior = this.behaviors.get(state);
    }

    // </editor-fold>
    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Result callback methods">
    public void betCoins(BetCoinsResult data) {
        PlayerCardInfo[] cards = data.getPlayerCards();
        PlayerInfo player = game.getCurrentPlayer();
        if (cards != null) {
            for (int i = 0; i < cards.length; i++) {
                PlayerCardInfo card = player.getCard(cards[i].getId());
                card.setOrder(cards[i].getOrder());
                card.setPile(cards[i].getPile());
            }
        }
        player.setCoins(data.getCoins());
    }

    public void buyCard(BuyCardResult data) {
        PlayerInfo player  = game.getCurrentPlayer();
        PlayerCardInfo playerCard = data.getPlayerCard();
        GameCardInfo gameCard = data.getGameCard();
        player.addCard(playerCard);
        game.getCard(gameCard.getId()).setCount(gameCard.getCount());
        player.setCoins(player.getCoins() - gameCard.getCost());
        player.setBuys(player.getBuys() - 1);
    }
    
    public void nextPlayer(NextPlayerResult data) {
        game.nextPlayer(data.getPlayer());
        this.setStateAndBehavior("Action");
    }
        
    public void playAction(PlayActionResult data) {
        this.setStateAndBehavior("Action");
    }

    public void startBet(StartBetResult data) {
        this.setStateAndBehavior("Bet");
    }

    public void startBuy(StartBuyResult data) {
        this.setStateAndBehavior("Buy");
    }

    public void startGame(StartGameResult data) {
        this.game = new Game(data);
        this.behavior = behaviors.get(this.game.getInfo().getState());
    }

    public void testServer(TestServerResult data) {
        System.out.println("Test server ok");
    }
    // </editor-fold>
    ////////////////////////////////////////////////////////////////////////////
}
