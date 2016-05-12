/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominion.model.database;

import java.sql.Connection;
import org.junit.Test;
import static org.junit.Assert.*;

public class MySQLConnectionProviderTest 
{
    @Test
    public void testGetConnection() throws Exception {
        System.out.println("getConnection");
        String host = "localhost";
        int port = 3306;
        String database = "dominion";
        String username = "root";
        String password = "";
        MySQLConnectionProvider instance = new MySQLConnectionProvider(
            host, port, database, username, password);
        try(Connection result = instance.getConnection()) {
            assertTrue(result != null);
        }
    }
}
