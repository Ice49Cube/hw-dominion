/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominion.commands;

import dominion.model.Game;
import dominion.model.database.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.sql.Connection;
import static org.junit.Assert.*;

/**
 *
 * @author SoftVibe
 */
public class PlayerTest {
    
    public PlayerTest() {}
    @BeforeClass
    public static void setUpClass() {}    
    @AfterClass
    public static void tearDownClass() {}
    @Before
    public void setUp() {}
    @After
    public void tearDown() {}

    /**
     * Test of fromGame method, of class Player.
     */
    @Test
    public void testFromGame() throws Exception
    {
        System.out.println("fromGame");
        Database database = getDatabase();
        try(Connection con = database.getConnection()) {
            Game game = new Game(con, new String[]{ "one", "two" }, "baw");
            dominion.model.Player source = game.getCurrentPlayer();
            //Player expResult = null;
            Player result = Player.fromGame(game, source);
            CurrentPlayer current = (CurrentPlayer)result;
            //assertEquals(expResult, result);
        }        
    }

    private Database getDatabase() throws Exception
    {
        String host = "localhost";
        int port = 3306;
        String database = "dominion";
        String username = "root";
        String password = "";
        String[] options = new String[]{ "useSSL=false" };
        return new Database(new MySQLConnectionProvider(
            host, port, database, username, password, options));  
    }    
}
