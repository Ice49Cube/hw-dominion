/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serializingtest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Michael
 */
public class GameTest {
    
    public GameTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of startNewGame method, of class Game.
     */
    @Test
    public void testIfRoutingReturnsStartNewGameResult() {
        System.out.println("startNewGame");
        String json = "{\"method\":\"startNewGame\",\"players\":[\"player 1\", \"player 2\", \"player 3\"]}";
        Game game = new Game();
        Routing routing = new Routing(game);
        StartNewGameResult result = (StartNewGameResult)routing.invoke(json);
        assertTrue(result.name.equals("COOL"));
        System.out.println(routing.resultToJson(result));
    }
}
