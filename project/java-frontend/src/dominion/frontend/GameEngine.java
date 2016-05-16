package dominion.frontend;


import dominion.results.*;

import dominion.frontend.behaviors.*;
//import dominion.frontend.responses.*;

import dominion.routing.CommandBase;
import dominion.routing.ResultBase;

import java.lang.reflect.Method;

public class GameEngine {

    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Constructors and member declarations" defaultstate="collapsed">
    private final Routing routing;

    private Game game;
    private IGameEngineBehavior behavior;
    private ResultBase response;

    public GameEngine(String url) {
        this.routing = new Routing(url, this);
        this.behavior = new StartGameBehavior();
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
    // </editor-fold>
    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Callback methods">

    public void startBuy(StartBuyResult data) {
        this.behavior = new BuyBehavior();
    }

    public void playAction(PlayActionResult data) {
        // todo: play action
    }

    public void startGame(StartGameResult data) {
        dominion.results.StartGameResult x = new dominion.results.StartGameResult();
        
        this.game = new Game(data);
        // Buy or actions behavior? Actions = 0, then buy...
        if (this.game.getState().equals("Action")) {
            this.behavior = new ActionBehavior();
        } else {
            this.behavior = new BuyBehavior();
        }
    }

    public void testServer(TestServerResult data) {
        System.out.println("Test server ok");
    }
    /*public void buyCard(BuyCardResult data) {
        
    }*/
    // </editor-fold>
    ////////////////////////////////////////////////////////////////////////////
}
