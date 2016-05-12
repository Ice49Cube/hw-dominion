package dominion.model.database;

import dominion.model.database.IConnectionProvider;
import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Uses the context.xml configuration in a servlet project to create connections.
 */
public class PooledMySQLConnectionProvider implements IConnectionProvider 
{
    private final Context initContext;
    private final Context envContext;
    private final DataSource dataSource;
    
    public PooledMySQLConnectionProvider() throws NamingException
    {
        this.initContext = new InitialContext();
        this.envContext = (Context)initContext.lookup("java:/comp/env");
        this.dataSource = (DataSource)envContext.lookup("jdbc/dom");
    }
    
    @Override
    public Connection getConnection() throws Exception
    {
        return dataSource.getConnection();
    }
}
