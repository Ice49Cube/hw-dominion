/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominion.model.database;

//import dominion.model.database.Database;
//import java.sql.ResultSet;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Maysam
 */
public class DatabaseTest2 {
    
    private IConnectionProvider provider;
    private Connection testConn;
    private PreparedStatement testPPStmt;

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
        this.provider = new MySQLConnectionProvider(host, port, database, username, password, options);
    }
    
    //these tests are done on "foo" table which has an id and game column.
    /*@Test 
    public void createTable() throws Exception
    {
        String sql = "CREATE TABLE foo( id INT NOT NULL AUTO_INCREMENT, game INT, PRIMARY KEY (id) )";
        Database dd = new Database(provider);
        int reply = dd.executeUpdate(sql);
        System.out.println("create it .... " + reply);
    }*/
    
    
    @Test
    public void testExecuteInsert() throws Exception
    {
        String sql = "INSERT INTO foo ( game ) VALUES ( 26 )";
        Database dd = new Database(provider);
        int reply = dd.executeInsert(sql);
        System.out.println("New ID: " + reply);
    }
    
    
    /*
    // not giving access to the executeQuery method inside the database class
    @Test
    public void testExecuteQuery() throws Exception
    {
        String sql = "SELECT id, game, NAME FROM players";
        Database dd = new Database(provider);
        ResultSet rs = testPPStmt.executeQuery(sql);
        while (rs.next())
        {
            int ID = rs.getInt("id");
            int Game = rs.getInt("game");
            String Name = rs.getString("name");
            System.out.println(ID + "\t" + Game + "\t" + Name);
        }
        assertEquals(null, testPPStmt.executeQuery(sql));
    }
    */
    
    @Test
    public void testExecuteUpdate() throws Exception
    {
        String sql = "DELETE FROM foo WHERE id = 19";
        Database dd = new Database(provider);
        int reply = dd.executeUpdate(sql);
        String result = (reply == 1) ? "Deleted!" : "record not found!";
        System.out.println(result);
    }
    
    
    @Test
    public void testExecute() throws Exception
    {
        String sql = "INSERT INTO foo ( game ) VALUES ( 16 )";
        Database dd = new Database(provider);
        Boolean reply = dd.execute(sql);
        String result = (reply == true) ? "adding new record failed!" : "new record added!";
        System.out.println(result);
    }
    

    /*
    @Test 
    public void dropTable() throws Exception
    {
        String sql = "DROP TABLE foo";
        Database dd = new Database(provider);
        dd.executeUpdate(sql);
        System.out.println("delete it");
    }
    */

}
