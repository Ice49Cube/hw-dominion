/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominion.model;

import dominion.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
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
public class RandomUnitTests {
    
    public RandomUnitTests() {
    }
    
    @BeforeClass
    public static void setUpClass() { }
    @AfterClass
    public static void tearDownClass() { }
    @Before
    public void setUp() { }
    @After
    public void tearDown() { }

    /**
     * We saw strings in class but not "Integer".
     */
    @Test
    public void test_Integer_equals() 
    {
        Integer i1 = new Integer(1);
        Integer i2 = new Integer(1);
        int i3 = 1;
        ArrayList<Integer> integers = new ArrayList();
        integers.add(i1);
        
        assertFalse(i1 == i2);
        assertTrue((int)i1 == (int)i2);
        assertTrue(i1.equals(i2));
        assertEquals(i1, i2);
        assertTrue(integers.contains(i2));
        
        assertTrue(i1 == i3);
        assertTrue((int)i1 == i3);
        assertTrue(i1.equals(i3));
        assertEquals(i1,(Object) i3);
        assertTrue(integers.contains(i3));
    }

    /**
     * And we didn't see strings in an arraylist, but :) if the above works
     * so should this...
     */
    @Test
    public void test_String_equals_in_arrayList() 
    {
        String s1 = new String("hello");
        String s2 = new String("hello");
        ArrayList<String> strings = new ArrayList();
        strings.add(s1);
        assertFalse(s1 == s2);
        assertTrue(s1.equals(s2));
        assertEquals(s1, s2);
        assertTrue(strings.contains(s2));
    }
    
    
    /**
     * Test how to count the total of a field in a object colletion using streams.
     */
    @Test
    public void test_countStreamCollectionItemMember()
    {
        ArrayList<Integer> randoms = Util.getRandomRange(10);
        HashMap<Integer, StreamFooObject> stream = new HashMap();
        int expResult  = 0;
        for(int i = 0; i < randoms.size(); i++) {
            StreamFooObject item = new StreamFooObject();
            item.n = randoms.get(i);
            expResult += item.n;
            stream.put(i, item);
        }
        Integer result = stream.values().stream().map(v -> v.n).mapToInt(Integer::intValue).sum();
        assertTrue(expResult == result);
    }
}
