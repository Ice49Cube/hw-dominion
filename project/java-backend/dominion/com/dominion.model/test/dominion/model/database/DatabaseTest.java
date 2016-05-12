package dominion.model.database;

//import dominion.model.database.Database;
import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseTest {
    
    private IConnectionProvider provider;
    
    public DatabaseTest() { }
    @BeforeClass
    public static void setUpClass() { }
    @AfterClass
    public static void tearDownClass() { }
    @After
    public void tearDown()  { }

    /**
     * Getting connection provider.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception
    {
        System.out.println("Loading connection provider...");
        String host = "localhost";
        int port = 3306;
        String database = "dominion";
        String username = "root";
        String password = "";
        String[] options = new String[]{"useSSL=false"};
        this.provider = new MySQLConnectionProvider(
            host, port, database, username, password, options);
    }

    /**
     * Test of getConnection method, of class Database.
     * @throws Exception
     */
    @Test
    public void testGetConnection() throws Exception {
        System.out.println("getConnection");
        Connection expResult = null;
        Database instance = new Database(this.provider);
        try(Connection result = instance.getConnection()) {
            assertNotEquals(expResult, result);
            result.close();
        }
    }

    /**
     * Test of execute method, of class Database.
     * @throws Exception
     */
    public void testExecute_Connection_String_ObjectArr() throws Exception
    {
        System.out.println("testExecute_Connection_String_ObjectArr");
        String sql = "SELECT ? = \"vozzie\"";
        Object[] args = new Object[]{ "vozzie" };
        Database instance = new Database(this.provider);
        boolean expResult = true;
        boolean result = instance.execute(sql, args);
        assertEquals(expResult, result);
    }

    /**
     * Test of executeInsert method, of class Database.
     * @throws Exception
     */
    @Test
    public void testInsert_Connection_String_ObjectArr() throws Exception
    {
        System.out.println("testInsert_Connection_String_ObjectArr");
        Object[] args = new Object[] { "goon"};
        Database instance = new Database(this.provider);
        int id1, id2;
        int exp1 = 1, exp2 = 2;
        try(Connection con = instance.getConnection()) {
            instance.execute("DROP TABLE IF EXISTS foo");
            instance.execute("CREATE TABLE foo (baw INT NOT NULL AUTO_INCREMENT, bar VARCHAR(20),  PRIMARY KEY (baw))");
            id1 = Database.executeInsert(con, "INSERT INTO foo (bar) VALUES (?)", args);
            id2 = Database.executeInsert(con, "INSERT INTO foo (bar) VALUES (?)", args);
            instance.execute("DROP TABLE foo");
        }
        assertEquals(exp1, id1);
        assertEquals(exp2, id2);               
    }
    
    /**
     * Test of executeQuery method, of class Database.
     * @throws Exception
     */
    @Test
    public void testQuery_Connection_String_ObjectArr() throws Exception
    {
        System.out.println("testQuery_Connection_String_ObjectArr");
        Object[] args = new Object[] { 1, "goon"};
        Database instance = new Database(this.provider);
        Connection con = instance.getConnection();
        instance.execute("DROP TABLE IF EXISTS foo");
        instance.execute("CREATE TABLE foo (baw INT, bar VARCHAR(20))");
        instance.execute("INSERT INTO foo (baw, bar) VALUES (1, \"goon\")");
        ResultSet result = Database.executeQuery(con, con.prepareStatement("SELECT * FROM foo WHERE baw = ? AND bar = ?"), args);
        if(result.next()) {
            assertEquals("goon", result.getString("bar"));
        } else fail("Could not read a record.");
        instance.execute("DROP TABLE foo");
    }

    /**
     * Test of test method, of class Database.
     */
    @Test
    public void testTest() 
    {
        System.out.println("test");
        Database instance = new Database(this.provider);
        boolean expResult = true;
        boolean result = instance.test();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of executeUpdate method, of class Database.
     * @throws Exception
     */
    @Test
    public void testUpdate_Connection_String_ObjectArr() throws Exception
    {
        System.out.println("testUpdate_Connection_String_ObjectArr");
        Object[] args = new Object[]{ "goon" };
        Database instance = new Database(this.provider);
        try(Connection con = instance.getConnection()) {
            Database.execute(con, "DROP TABLE IF EXISTS foo");
            Database.execute(con, "CREATE TABLE foo (bar VARCHAR(20))");
            Database.execute(con, "INSERT INTO foo (bar) VALUES (\"goon\")");
            Database.execute(con, "INSERT INTO foo (bar) VALUES (\"goony\")");
            int updatedRows = Database.executeUpdate(con, "UPDATE foo SET bar=\"goony\" WHERE bar = ?", args);
            int deletedRows = Database.executeUpdate(con, "DELETE FROM foo");
            Database.execute(con, "DROP TABLE foo");
            assertEquals(1, updatedRows);    
            assertEquals(2, deletedRows);    
        }
    }
}