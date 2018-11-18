import java.sql.*;
import java.io.*;
import java.util.*;

/* Author: student CS 211E Irina Golovko
* Date  :   02/17/2018
* Quiz : #1 DemoQuiz.java
* Objective : This client program connect to the ORACLE DataBase,
* on dunes.ccsf.edu server, check if table STUDENT exist: if yes -
* do some query, if no - create and populate it
* with ID, studentId and class.  
/**********************************************************/

public class DemoQuiz
{
    static final String UNAME = "igolovko";
	static final String PASSWD = "qaz123qaz";
//*****************************tableExists*****************************
// This method returns false if table doesn't exists
	public static boolean tableExists(Statement stmt, String tableName) 
	{
		boolean exist = true;
		String owner = "IGOLOVKO";
		try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " 
							+ "all_tables WHERE table_name = '" + 
							tableName + "' AND owner = '" + owner + "'"))
		{
			rs.next();
			int find = rs.getInt(1);
			if (find == 0)
				exist = false;
			else
				exist = true;
		}
		catch (SQLException e) 
	 	{
	 		System.err.println(e);
	 	}
		return exist;
	} 
	
    public static boolean tableEx(Connection conn, String tableName) throws SQLException
       boolean tExists = false;
       try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null))
	    {
           while (rs.next()) 
		   {
               String tName = rs.getString("STATE_CAPITAL");
               if (tName != null && tName.equals(tableName)) 
			   {
                   tExists = true;
                   System.out.println("STATE_CAPITAL table is already exists!");
                   break;
               }
   		   }
       }
       return tExists;
   }
	
	
	
	
	
//*****************************dbConnect***************************************
    public static Connection dbConnect(String userN, String passwd) 
    {
		Connection conn = null;
		try 
		{ 
			String url = "jdbc:oracle:thin:@//dunes.ccsf.edu:1521/orcl.ccsf.edu";
       		conn = DriverManager.getConnection(url, userN, passwd);	
		}
		catch(Exception e)
		{
		
		
		
		
		
			System.err.println(e);
		} 
		return conn;	
	}
//*****************************countRows*****************************************
// This method returns namber of rows in the table	
    public static int countRows(Statement stmt, String tableName) 
	{
		int count = -1;
		try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " 
											+ tableName))
		{
	  		rs.next();
         	count = rs.getInt(1);
			
       } 
	   catch (SQLException e) 
	   {
		   System.err.println(e);
	   }
	   return count;
	}
//*****************************main*****************************************	
    public static void main (String ... args) 
    {
		try ( Connection conn = dbConnect(UNAME, PASSWD);
              Statement stmt = conn.createStatement())
       {
           conn.setAutoCommit(false);
		   String tN = "STUDENT";
		   
		if (tableEx(stmt, tN))
			System.out.println("Table " + tN + 
				" already exists.");		
		   
			//check if table exists
			if (tableExists(stmt, tN))
				System.out.println("Table " + tN + 
					" already exists.");		
					
			else
			{
				System.out.println("Table " + tN + 
					" doesn't exists.");
				System.out.println("Starts to create table " + tN);
				stmt.executeUpdate("CREATE TABLE " + tN + 
					"(id number generated "
					+ "by default on null as identity,"
					+ " studentID varchar2(15),"
					+ " class varchar2(10))");
			}
				
	    	//check if table already have a value
			int c = countRows(stmt, tN); 
	    	if (c != 0) 
			{
		  	  System.out.println("Table " + tN + " already have a data.");
	    	} 
			
			else
			{
				System.out.println("Now we will populate table " + tN + 
					 				" with some data.");
				
				stmt.executeUpdate("INSERT INTO " + tN + 
					"(studentID, class)"
						+ " VALUES('igolovko', 'CS211e')");
				stmt.executeUpdate("INSERT INTO " + tN + 
					"(studentID, class)" 
					+ " VALUES('idubinin', 'CS211e')");
				stmt.executeUpdate("INSERT INTO " + tN + 
					"(studentID, class)"
				+ " VALUES('jpeck', 'CS260a')");
				stmt.executeUpdate("INSERT INTO " + 
					tN + "(studentID, class)"
					+ " VALUES('wliano', 'CS260a')");
				stmt.executeUpdate("INSERT INTO " 
					+ tN + "(studentID, class)"
					+ " VALUES('kdole', 'CNIT132')");
				stmt.executeUpdate("INSERT INTO " 
					+ tN + "(studentID, class)"
					+ " VALUES('gsilva', 'CNIT132')");
				stmt.executeUpdate("INSERT INTO " + tN 
					+ "(studentID, class)"
				+ " VALUES('abolkhov', 'CS177')");		
			}
			System.out.println("Now we will execute some queries:");
			// Select all states starts with O
			System.out.println("Show all classes starts with CNIT");
			String  query = "SELECT * FROM " + tN + " WHERE class LIKE 'CNIT%'";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
			{
				System.out.println("ID: " + rs.getInt(1) + " studentID: " +
	  					             rs.getString(2) + " class: " 
									 + rs.getString(3));
			}		
		}
   		catch (Exception e) 
   		{
         	System.err.println(e);
		}     
  	}
}
		   
	