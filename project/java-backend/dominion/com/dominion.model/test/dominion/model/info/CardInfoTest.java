package dominion.model.info;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test the CardInfo class that contains info to create a new game.
 */
public class CardInfoTest {
    
    public CardInfoTest() { }
    @BeforeClass
    public static void setUpClass() { }
    @AfterClass
    public static void tearDownClass() { }
    @Before
    public void setUp() { }
    @After
    public void tearDown() { }

    /**
     * Test of values method, of class CardInfo.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        CardInfo[] expResult = new CardInfo[] {
            CardInfo.Gold,
            CardInfo.Silver,
            CardInfo.Copper,
            CardInfo.Adventurer,
            CardInfo.Bureaucrat,
            CardInfo.Cellar,
            CardInfo.Chancellor,
            CardInfo.Chapel,
            CardInfo.CouncilRoom,
            CardInfo.Feast,
            CardInfo.Festival,
            CardInfo.Gardens,
            CardInfo.Laboratory,
            CardInfo.Library,
            CardInfo.Market,
            CardInfo.Militia,
            CardInfo.Mine,
            CardInfo.Moat,
            CardInfo.Moneylender,
            CardInfo.Remodel,
            CardInfo.Smithy,
            CardInfo.Spy,
            CardInfo.Thief,
            CardInfo.ThroneRoom,
            CardInfo.Village,
            CardInfo.Witch,
            CardInfo.Woodcutter,
            CardInfo.Workshop,
            CardInfo.Province,
            CardInfo.Duchy,
            CardInfo.Estate,
            CardInfo.Curse
        };
        CardInfo[] result = CardInfo.values();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of valueOf method, of class CardInfo.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "Adventurer";
        CardInfo expResult = CardInfo.Adventurer;
        CardInfo result = CardInfo.valueOf(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCost method, of class CardInfo.
     */
    @Test
    public void testGetCost() {
        System.out.println("getCost");
        assertEquals(CardInfo.Adventurer.getCost(), 6);
    }

    /**
     * Test of getCount method, of class CardInfo.
     */
    @Test
    public void testGetCount() {
        System.out.println("getCount");
        
        java.util.Random random = new java.util.Random();
        Date date = new Date();
        random.setSeed(date.getTime());
        assertEquals(10, CardInfo.Curse.getCount(2));
        assertEquals(20, CardInfo.Curse.getCount(3));
        assertEquals(30, CardInfo.Curse.getCount(4));
        
        assertEquals(30, CardInfo.Gold.getCount(2));
        assertEquals(40, CardInfo.Silver.getCount(3));
        assertEquals(32, CardInfo.Copper.getCount(4));
        
        assertEquals(8, CardInfo.Estate.getCount(2));
        assertEquals(12, CardInfo.Duchy.getCount(3));
        assertEquals(12, CardInfo.Province.getCount(4));
        
        CardInfo[] randomCards = CardSet.getRandomCardSet().getCards();
        for(CardInfo card : randomCards)
        {
            int numberOfPlayers = random.nextInt(3) + 2;
            int count = card.getCount(numberOfPlayers);
            System.out.println(card.getName());
            assertEquals(10, count);
        }
    }

    /**
     * Test of getName method, of class CardInfo.
     */
    @Test
    public void testGetName_and_testFormatNameCasing() {
        System.out.println("getName formatNameCasing");
        CardInfo[] cards = CardInfo.values();
        for(CardInfo card : cards)
            assertEquals(card, CardInfo.valueOf(CardInfo.formatNameCasing(card.getName())));
    }
}
