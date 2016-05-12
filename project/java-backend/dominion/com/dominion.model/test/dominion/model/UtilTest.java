/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominion.model;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author SoftVibe
 */
public class UtilTest {
    
    public UtilTest() {
    }

    /**
     * Test of getRange method, of class Util.
     */
    @Test
    public void testGetRandomRange() {
        System.out.println("getRange");
        int size = 5;
        int min = 3;
        int max = 8;
        ArrayList<Integer> result = Util.getRandomRange(size, min, max);
        assertTrue(result.contains(3));
        assertTrue(result.contains(4));
        assertTrue(result.contains(5));
        assertTrue(result.contains(6));
        assertTrue(result.contains(7));
        assertTrue(result.size() == 5);
        size = 5;
        max = 5;
        result = Util.getRandomRange(size, 0, max);
        assertTrue(result.contains(0));
        assertTrue(result.contains(1));
        assertTrue(result.contains(2));
        assertTrue(result.contains(3));
        assertTrue(result.contains(4));
        assertTrue(result.size() == 5);
        size = 5;
        result = Util.getRandomRange(size);
        assertTrue(result.contains(0));
        assertTrue(result.contains(1));
        assertTrue(result.contains(2));
        assertTrue(result.contains(3));
        assertTrue(result.contains(4));
        assertTrue(result.size() == 5);
        try
        {
            max = 4;
            size = 5;
            result = Util.getRandomRange(size, max);
            fail("No exeption was thrown...");
        }
        catch(Exception ex)
        { 
        }
    }
}
