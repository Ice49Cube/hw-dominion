package dominion.routing;


import dominion.routing.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.logging.*;

import org.junit.*;
import static org.junit.Assert.*;

/*

Post request with command line, 
1. telnet hostname port
2. paste request

POST /Server HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 95

{"_method_":"startNewGame","playerNames":["One","Two","Three"],"gameType":"The Defensive King"}

Some javascript to build the request

var json = JSON.stringify({
    "_method_": "startNewGame",
    "playerNames": ["One", "Two", "Three"],
    "gameType": "The Defensive King"
});
console.log("Content-Length: " + json.length + "\r\n\r\n" + json + "\r\n")
*/

/**
 * Unit test for the Routing class.
 * @author Michael
 */
public class RoutingTest {
    
    public RoutingTest() {
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
     * Test of constructor, of class Routing, with a valid class argument.
     */
    @Test
    public void test_constructor_ValidClass()
    {
        try 
        {
            System.out.println("Test of constructor, of class Routing, with a valid class argument.");
            ValidClass game = new ValidClass();
            Routing routing = new Routing(game);
            assertNotNull(routing);
            System.out.println("Test succeeded.");
        } catch (Exception ex) {
            Logger.getLogger(RoutingTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("The test failed!");
        }
    }   

    @Test
    public void test_execute_ValidClass_validCommand() 
    {
        try
        {
            System.out.println("Test of execute method, of class Routing, with a valid class argument and a valid command.");
            String expResult = "{\"success\":true,\"method\":\"validMethod\"}";
            String result = executeJsonWithValidClass("validCommand.json");
            System.out.println("Result: " + result);
            assertEquals(expResult, result);
            System.out.println("Test succeeded.");
        } catch (Exception ex) {
            Logger.getLogger(RoutingTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Test failed!");
            
        }
    }
    
    @Test
    public void test_execute_ValidClass_invalidCommandNoMethod() 
    {
        try
        {
            System.out.println("Test of execute method, of class Routing, with a valid class argument and an invalid command missing the method attribute.");
            String expResult = "{\"success\":false,\"method\":null,\"error\":{\"message\":\"Method field '_method_' is missing.\",\"methodField\":\"_method_\"}}";
            String result = executeJsonWithValidClass("invalidCommandNoMethod.json");
            System.out.println("Result: " + result);
            assertEquals(expResult, result);
            System.out.println("Test succeeded.");
        } catch (Exception ex) {
            Logger.getLogger(RoutingTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Test failed!");
        }
    }
    
    @Test
    public void test_execute_ValidClass_invalidCommandMethodDoesNotExist() 
    {
        try
        {
            System.out.println("Test of execute method, of class Routing, with a valid class argument and an invalid command with a method that does not exist.");
            String expResult = "{\"success\":false,\"method\":null,\"error\":{\"message\":\"Method 'THIS METHOD DOES NOT EXIST' could not be resolved.\",\"methodName\":\"THIS METHOD DOES NOT EXIST\"}}";
            String result = executeJsonWithValidClass("invalidCommandMethodDoesNotExist.json");
            System.out.println("Result: " + result);
            assertEquals(expResult, result);
            System.out.println("Test succeeded.");
        } catch (Exception ex) {
            Logger.getLogger(RoutingTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Test failed!");
        }
    }    
    
    @Test
    public void test_execute_ValidClass_invalidCommandMissingAttribute() 
    {
        try
        {
            System.out.println("Test of execute method, of class Routing, with a valid class argument and an invalid command with a attribute that doesn not exist.");
            String expResult = "{\"success\":false,\"method\":null,\"error\":{\"message\":\"Property with name 'someBool' is missing for command 'validMethod'.\",\"methodName\":\"validMethod\",\"propertyName\":\"someBool\"}}";
            String result = executeJsonWithValidClass("invalidCommandMissingAttribute.json");
            System.out.println("Result: " + result);
            assertEquals(expResult, result);
            System.out.println("Test succeeded.");
        } catch (Exception ex) {
            Logger.getLogger(RoutingTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Test failed!");
        }
    }
    
    @Test
    public void test_execute_ValidClass_invalidCommandMalformed() 
    {
        try
        {
            System.out.println("Test of execute method, of class Routing, with a valid class argument and an invalid command with malformed JSON.");
            String result = executeJsonWithValidClass("invalidCommandMalformed.json.txt");
            System.out.println("Result: " + result);
            assertTrue(result.contains("\"success\":false"));
            assertTrue(result.contains("\"method\":null"));
            assertTrue(result.contains("\"success\":false"));
            assertTrue(result.contains("\"message\":\"Illegal unquoted character ((CTRL-CHAR, code 13)): has to be escaped using backslash to be included in name\\n at [Source: java.io.BufferedReader@"));
            System.out.println("Test succeeded.");
        } catch (Exception ex) {
            Logger.getLogger(RoutingTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Test failed!");
        }
    }
    
    @Test
    public void test_execute_ValidClass_validMethodWithComplexCommand() 
    {
        try
        {
            System.out.println("Test of execute method, of class Routing, with a valid class argument and a valid complex command.");
            String expResult = "{\"success\":true,\"method\":\"validMethodWithComplexCommand\"}";
            String result = executeJsonWithValidClass("validMethodWithComplexCommand.json");
            System.out.println("Result: " + result);
            assertEquals(expResult, result);
            System.out.println("Test succeeded.");
        } catch (Exception ex) {
            Logger.getLogger(RoutingTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Test failed!");
        }
    }
    
    @Test
    public void test_execute_ValidClass_invalidMethodWithComplexCommandMissingAttribute() 
    {
        try
        {
            System.out.println("Test of execute method, of class Routing, with a valid class argument and an invalid complex command with a missing attribute.");
            String expResult = "{\"success\":false,\"method\":null,\"error\":{\"message\":\"Unrecognized field \\\"nope\\\" (class dominion.routing.User), not marked as ignorable (one known property: \\\"name\\\"])\\n at [Source: N/A; line: -1, column: -1] (through reference chain: dominion.routing.ValidComplexCommand[\\\"user\\\"]->dominion.routing.User[\\\"nope\\\"])\"}}";
            String result = executeJsonWithValidClass("invalidMethodWithComplexCommandMissingAttribute.json");
            System.out.println("Result: " + result);
            assertEquals(expResult, result);
            System.out.println("Test succeeded.");
        } catch (Exception ex) {
            Logger.getLogger(RoutingTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Test failed!");
        }
    }   
    
  /*  @Test
    public void test_exception_ValidClass_validException() 
    {
        try
        {
            System.out.println("Test of exception method, of class Routing, with a valid class argument and valid exception.");
            String result = exceptionToJson(new IOException("TEST MESSAGE"), "TEST INFORMATION");
            System.out.println("Result: " + result);
            assertTrue(result.contains("{\"success\":false"));
            System.out.println("Test succeeded.");
        } catch (Exception ex) {
            Logger.getLogger(RoutingTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Test failed!");
        }
    }*/   
    
    ///////////////            HELPER METHODS          /////////////

   /* private static String exceptionToJson(Exception ex, String information) throws IOException
    {
        Routing routing = new Routing(new ValidClass());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        routing.exception(ex, information, pw);
        return sw.getBuffer().toString();
    } */
    
    private static String executeJsonWithValidClass(String resourceName)
            throws Exception
    {
        ValidClass target = new ValidClass();
        Routing routing = new Routing(target);
        BufferedReader validReader = getJsonResource(resourceName);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ResultBase result = routing.execute(validReader);
        routing.writeResult(pw, result);
        return sw.getBuffer().toString();
    }
    
    private static BufferedReader getJsonResource(String name) throws Exception 
    {        
        URL url = RoutingTest.class.getResource("./resources/" + name);
        URI uri = new URI(url.toString());
        Path path = Paths.get(uri);
        byte[] bytes = Files.readAllBytes(path);
        return new BufferedReader(new StringReader(new String(bytes, "UTF-8")));
    }
}
