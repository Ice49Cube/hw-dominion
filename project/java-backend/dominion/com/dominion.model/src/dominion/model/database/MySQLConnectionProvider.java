package dominion.model.database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Is used by the JUnit tests outside the servlet (eclipse) environment in 
 * netbeans.
 */
public class MySQLConnectionProvider implements IConnectionProvider
{
    private static final String DRIVER = "com.mysql.jdbc.Driver";  
    private static final String PROTOCOL = "jdbc:mysql://";
    
    private final String url;
    private final String username;
    private final String password;
    
    
    public MySQLConnectionProvider(String host, int port, String database, String username, String password) throws ClassNotFoundException
    {
        this(host, port, database, username, password, null);
    }
    
    public MySQLConnectionProvider(String host, int port, String database, String username, String password, String[] options) throws ClassNotFoundException
    {
        this.url = PROTOCOL + host + ":" + (Integer)port + "/" + database + (options != null ? "?" + String.join("&", options) : "");
        this.username = username;
        this.password = password;
        Class.forName(DRIVER);
    }
  
    @Override
    public Connection getConnection() throws Exception 
    {
        return DriverManager.getConnection(this.url, this.username, this.password);
    }    
}