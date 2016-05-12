/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc_module;
import java.sql.*;
import java.util.Random;


/**
 *
 * @author Maysam
 */
public class databaseLink 
{
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost:3306/dominion";

   //  Database credentials
   static final String USER = "dominion";
   static final String PASS = "dominion";
   
   
   public void databankLink(String arg1, String arg2, int arg3)
   {
        Connection conn = null;
        Statement stmt = null;
        
        try
        {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //generating a random number for wordnr use         
            RandomGenerator rangentest = new RandomGenerator();
            
            //concatinating string with integer for using as a sql statement
            String cmd = "SELECT " + arg1 + " FROM " + arg2 + " where wordnr=";  //SELECT wordnr, word, disabled FROM words where wordnr=
            StringBuilder cmdStr = new StringBuilder (String.valueOf(cmd));
            cmdStr.append(arg3);    //rangentest.RandonGenerator()
            String fullCMD = cmdStr.toString();
            
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = fullCMD;
            ResultSet rs = stmt.executeQuery(sql);
            

          //STEP 5: Extract data from result set
        while(rs.next())
        {
             //Retrieve by column name
            int wordnr = rs.getInt("wordnr");
            String word = rs.getString("word");
            boolean disabled = rs.getBoolean("disabled");

            //storing the data in a class and getting it back and displaying its values.
            Words toClass = new Words();
            toClass.setWordList(wordnr, word, disabled);
            int wordnrget = toClass.getWordnr();
            String wordget = toClass.getWord();
            boolean disabledget = toClass.getDisabled();
            System.out.println(wordnrget + " | " + wordget + " | " + disabledget);
            
            
             //Display values
//            System.out.print(", index: " + wordnr);
//            System.out.print(", word: " + word);
//            System.out.println(", state: " + disabled);
          }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }
        catch(SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        catch(Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        finally
        {
          //finally block used to close resources
            try
            {
                if(stmt!=null)
                    stmt.close();
            }
            catch(SQLException se2)
            {
                
            }// nothing we can do
            try
            {
                if(conn!=null)
                    conn.close();
            }
            catch(SQLException se)
            {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
   
}
