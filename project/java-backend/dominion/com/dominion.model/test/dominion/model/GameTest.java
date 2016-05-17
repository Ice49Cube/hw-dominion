package dominion.model;

import dominion.model.database.Database;
import dominion.model.database.MySQLConnectionProvider;
import java.sql.Connection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest 
{
    public GameTest() { }
    @BeforeClass
    public static void setUpClass() { }
    @AfterClass
    public static void tearDownClass() { }
    @Before
    public void setUp() { }
    @After
    public void tearDown() { }
    
    private MySQLConnectionProvider getProvider() throws Exception
    {
        String host = "localhost";
        int port = 3306;
        String database = "dominion";
        String username = "root";
        String password = "";
        String[] options = new String[]{ "useSSL=false" };
        return new MySQLConnectionProvider(
            host, port, database, username, password, options);  
    }
    
    /**
     * Test of constructors, of class Game.
     */
    @Test
    public void testConstructors() throws Exception {
        System.out.println("new Game(Database database, String[] playerNames, String cardSet)");
        Database database = new Database(getProvider());
        try(Connection con = database.getConnection()) {
            String[] playerNames = new String[] { "one", "two", "three" };
            String cardSetName = "Nope";
            Game result = new Game(con, playerNames, cardSetName);
            Game expResult = new Game(con, result.getId());
            assertEquals("Game id doesn't match.", expResult.getId(), result.getId());
            //assertEquals("Current player doesn't match.", expResult.getCurrentPlayerId(), result.getCurrentPlayerId());
            //    System.out.println(result.getCurrentPlayer().getCoins());
                    
            //Player player = result.getCurrentPlayer();
            //player.getCards();
        }
    }

    /**
     * Test of isFinished method, of class Game, when three card piles are empty.
     */
    @Test
    public void testIsFinished_three_card_piles_empty() throws Exception {
        System.out.println("isFinished");
        Database database = new Database(getProvider());
        try(Connection con = database.getConnection()) {
            Game instance = new Game(con, new String[]{"Jaan", "Wout"}, "Interaction");
            boolean expResult = false;
            boolean result = instance.isFinished(database.getConnection());
            assertEquals(expResult, result);

            // todo: empty 3 stacks in gamecards of the current game, but not the province stack

            // todo: test with one empty
            //expResult = true;
            //result = instance.isFinished(con);
            //assertEquals(expResult, result);

            // todo: test with two empty
            //expResult = true;
            //result = instance.isFinished(con);
            //assertEquals(expResult, result);
        }
    }

    /**
     * Test of isFinished method, of class Game, when the province pile is empty.
     */
    @Test
    public void testIsFinished_province_pile_empty() throws Exception {
        System.out.println("isFinished");
        Database database = new Database(getProvider());
        try(Connection con = database.getConnection()) {
            Game instance = new Game(con, new String[] {"Jaan", "Wout"}, "Interaction");
            boolean expResult = false;
            boolean result = instance.isFinished(con);
            assertEquals(expResult, result);

            GameCard[] cards = instance.getCards();
            cards = null;
            // todo: clear province cards
            //expResult = true;
            //result = instance.isFinished(con);
            //assertEquals(expResult, result);
        }
    }
}
