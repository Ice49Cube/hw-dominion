package dominion.routing;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import com.fasterxml.jackson.databind.*;

/**
 *
 * @author SoftVibe
 */
public class RandomTests {
    
    public RandomTests() {
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
     * Not a real test, just to see if something works...
     */
    @Test
    public void test_serialize_super_sub() throws Exception {
        SuperClass[] classes = new SuperClass[2];
        classes[0] = new SubClass();
        classes[1] = new SuperClass();
        String json = new ObjectMapper().writeValueAsString(classes);
        System.out.println(json);
    }
}
