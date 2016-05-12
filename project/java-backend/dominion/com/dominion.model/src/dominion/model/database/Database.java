package dominion.model.database;

import java.sql.*;

public class Database 
{   
    private final IConnectionProvider provider;
    
    public Database(IConnectionProvider provider)
    {
        this.provider = provider;
    }
    
    public boolean execute(String sql) throws Exception
    {
        return execute(sql, new Object[]{ });
    }

    public boolean execute(String sql, Object[] args) throws Exception
    {
        try(Connection con = provider.getConnection()) {
            return execute(con, sql, args);
        }
    }

    public static boolean execute(Connection con, String sql) throws Exception
    {
        return execute(con, sql, new Object[] {});
    }

    public static boolean execute(Connection con, String sql, Object[] args) throws Exception
    {
        boolean result = false;
        try(PreparedStatement stmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            try
            {
                setStatementArguments(stmt, args);
                result = stmt.execute();
                con.commit();			
            }
            finally
            {
                 con.setAutoCommit(true);
            }
        }
        return result;
    }

    public int executeInsert(String sql) throws Exception
    {
        return executeInsert(sql, new Object[]{});
    }
    
    public int executeInsert(String sql, Object[] args) throws Exception
    {
        try(Connection con = getConnection()) {
            return executeInsert(con, sql, args);
        }
    }
    
    public static int executeInsert(Connection con, String sql) throws Exception
    {
        return executeInsert(con, sql, new Object[]{});
    }
    
    public static int executeInsert(Connection con, String sql, Object[] args) throws Exception
    {
        int id = -1;
        try(PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        	con.setAutoCommit(false);
        	try
        	{
                setStatementArguments(stmt, args);
                stmt.execute();
                try(ResultSet rs = stmt.getGeneratedKeys()) {
                    if(rs != null && rs.next())
                        id = rs.getInt(1);
                }
                if (id == -1) throw new IllegalStateException("Insert failed.");
        	}
        	finally
        	{
        		con.setAutoCommit(true);
        	}
        }
        return id;
    }
    
    public static ResultSet executeQuery(Connection con, PreparedStatement stmt) throws Exception
    {
        return executeQuery(con, stmt, new Object[]{});
    }

    public static ResultSet executeQuery(Connection con, PreparedStatement stmt, Object[] args) throws Exception
    {
        ResultSet result = null;
        con.setAutoCommit(false);
        try
        {
            setStatementArguments(stmt, args);
            result = stmt.executeQuery();
            con.commit();
        }
        finally
        {
            con.setAutoCommit(true);
        }
        return result;
    }
    
    public int executeUpdate(String sql) throws Exception
    {
        return executeUpdate(sql, new Object[]{});
    }
	
    public int executeUpdate(String sql, Object[] args) throws Exception
    {
        try(Connection con = provider.getConnection()) {
            return executeUpdate(con, sql, args);
        }		
    }
    
    public static int executeUpdate(Connection con, String sql) throws Exception
    {
        return executeUpdate(con, sql, new Object[]{ });
    }

    public static int executeUpdate(Connection con, String sql, Object[] args) throws Exception
    {
        int affectedRows = -1;
        try(PreparedStatement stmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            try
            {
                setStatementArguments(stmt, args);
                affectedRows = stmt.executeUpdate();
                con.commit();			
            }
            finally
            {
                con.setAutoCommit(true);
            }
        }
        return affectedRows;
    }
	
    public Connection getConnection() throws Exception
    {
    	Connection  result;
    	result = this.provider.getConnection();
        return result;
    }

    private static void setStatementArguments(PreparedStatement stmt, Object[] args) throws Exception
    {
        for(int index = 1; index <= args.length; index++)
        {
            Object arg = args[index - 1];
            if(arg instanceof String)
            {
                stmt.setString(index, (String)arg);					
            }
            else if(arg instanceof Integer)
            {
                stmt.setInt(index, (Integer)arg);								
            }
            else if(arg instanceof Date)
            {
                stmt.setDate(index, (Date)arg);
            }
            else
            {
                String message = "Unknown type of argument or wrong interpretation... check code!";
                System.out.println(message);
                throw new Exception(message);
            }
        }
    }

    public boolean test()
    {
        Connection connection;
        boolean result;
        try {
            connection = this.provider.getConnection();
            result = !connection.isClosed();
            connection.close();
        } catch(Exception e) {
            e.printStackTrace();
            result = false;
        } 
        return result;
    }
}
