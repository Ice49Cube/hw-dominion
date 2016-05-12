package dominion.model.info;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test the CardSet class that contains info to create a new game.
 */
public class CardSetTest {
    
    public CardSetTest() { }
    
    @BeforeClass
    public static void setUpClass() { }
    @AfterClass
    public static void tearDownClass() { }
    @Before
    public void setUp() { }
    @After
    public void tearDown() { }

    /**
     * Test of values method, of class CardSet.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        CardSet[] expResult = new CardSet[] { 
            CardSet.FirstGame,
            CardSet.BigMoney,
            CardSet.Interaction,
            CardSet.SizeDistortion,
            CardSet.VillageSquare
        };
        CardSet[] result = CardSet.values();
        assertArrayEquals(expResult, Arrays.copyOf(result, result.length-1));
    }

    /**
     * Test of valueOf method, of class CardSet.
     */
    @Test
    public void testValueOf_getName() {
        System.out.println("valueOf");
        try
        {
            CardSet.valueOf("baw baw").getName();
            fail("valueOf(\"baw baw\") did not cause an error");
        }
        catch(Exception ex)
        { }
        assertEquals(CardSet.Random.getName(), CardSet.valueOf("Random").getName());
        assertEquals(CardSet.FirstGame.getName(), CardSet.valueOf("FirstGame").getName());
        assertEquals(CardSet.BigMoney.getName(), CardSet.valueOf("BigMoney").getName());
        assertEquals(CardSet.Interaction.getName(), CardSet.valueOf("Interaction").getName());
        assertEquals(CardSet.SizeDistortion.getName(), CardSet.valueOf("SizeDistortion").getName());
        assertEquals(CardSet.VillageSquare.getName(), CardSet.valueOf("VillageSquare").getName());
    }

    /**
     * Test of getCards method, of class CardSet.
     */
    @Test
    public void testGetCards() {
        System.out.println("getCards");
        CardSet instance = CardSet.BigMoney;
        CardInfo[] expResult = new CardInfo[] {
            CardInfo.Adventurer, CardInfo.Bureaucrat, CardInfo.Chancellor, CardInfo.Chapel, CardInfo.Feast,
            CardInfo.Laboratory, CardInfo.Market, CardInfo.Mine, CardInfo.Moneylender, CardInfo.ThroneRoom
        };
        CardInfo[] result = instance.getCards();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getRandomCardSet method, of class CardSet.
     */
    @Test
    public void testGetRandomCardSet() {
        System.out.println("getRandomCardSet");
        CardSet result = CardSet.getRandomCardSet();
        assertEquals(10, result.getCards().length);
    }
    
    
    /**
     * Test if two random cardsets are different.
     */
    @Test
    public void testCardSet_Random_different() 
    {
        try
        {
            System.out.println("CardSet.Random");
            CardSet set1 = CardSet.Random;
            CardSet set2 = CardSet.Random;
            CardInfo [] cards1 = set1.getCards();
            // Getting 2 random sets on "the same time" causes them to be
            // the same because the random seed is based on the time.
            Thread.sleep(1);
            CardInfo [] cards2 = set2.getCards();
            boolean theSame = true;
            for(int i = 0; i < cards1.length && theSame; i++)
                if(!cards1[i].equals(cards2[i]))
                    theSame = false;
            if(theSame)
                fail("Car sets are the same...");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail("Unexpected exception");
        }
    }
}
