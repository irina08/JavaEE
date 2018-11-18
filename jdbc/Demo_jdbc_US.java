import java.sql.*;
import java.io.*;
import java.util.*;

/* Author: student CS 211E Irina Golovko
* Date  :   02/10/2018
* Homework assignment : #2 Demo_jdbc_US program.
* Objective : This client program connect to the ORACLE DataBase,
* on dunes.ccsf.edu server, creates table state_capital, populate it
* with name of US states and capital from given file US_states,
* executes some queres.  
/**********************************************************/

public class Demo_jdbc_US
{
 //*****************************dbConnect*****************************************
    public static Connection dbConnect(String userN, String passwd) throws SQLException
    {
		String url = "jdbc:oracle:thin:@//dunes.ccsf.edu:1521/orcl.ccsf.edu";
       	Connection conn = DriverManager.getConnection(url, userN, passwd);
       	return conn;
	}

//*****************************countRows*****************************************
// This method returns namber of rows in the table	
    public static int countRows(Connection conn, String tableName) 
	{
		Statement stmt = null;
		ResultSet rs = null;
		int count = 0;
       	try 
	   	{
         	stmt = conn.createStatement();
         	rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName);
  		  	rs.next();
         	count = rs.getInt(1);
       } 
	   catch (SQLException e) 
	   {
		   System.err.println(e);
	   }
	   finally 
	   {
         try
		 {
			 rs.close();
         	 stmt.close();
		 }
  	   	 catch (SQLException e)
		 {
  		   	 System.err.println(e);
  	     }
       }
       return count;
	}

//*****************************exists*****************************************
// This method returns 0 if table doesn't exists
	public static int exists(Connection conn, String tableName) throws SQLException 
	{
		int find = 0;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM all_tables WHERE " +
						 	 	" table_name = '" + tableName + "' AND owner='igolovko'");
		rs.next();
		find = rs.getInt(1);
		rs.close();
		stmt.close();
		return find;
	}

	//****************************parseUSStates()*****************************
	private static void parseUSStates(String states[], String capitals[])
	{
		try
		{
			Scanner sc = new Scanner(new File("US_states"));
			String line;
			int i = 0;
         
			sc.nextLine(); sc.nextLine(); // skip over couple of headers

			while(sc.hasNext())
			{
				line = sc.nextLine();
	            String temp[] = line.split("\\s\\s+");
	            if(temp.length >= 2)
	            {
					if(temp.length == 2) 
					{
						states[i]     = temp[0];
						capitals[i++] = temp[1];
					}
					else
					{
						states[i]     = temp[0] + " " + temp[1];
						capitals[i++] = temp[2];
					}
				}
	         }
                
	      }
		  catch(FileNotFoundException e)
		  {
			  System.err.println(e);
		  }
	  }
 //***********************displayQuery********************************************
	public static void displayQuery(Connection conn, String query) 
	{
		Statement stmt = null;
		ResultSet rs = null;
		try 
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next())
			{
				System.out.println("ID: " + rs.getInt(1) + " state: " +
	  					             rs.getString(2) + " capital: " + rs.getString(3));
			}		
	  	}
	  	catch (SQLException e) 
		{
	  		System.err.println(e);
		}
		finally 
		{
			try
			{
				rs.close();
				stmt.close();
			}
			catch (SQLException e)
			{
				System.err.println(e);
			}
		}
	}
//*****************************main*****************************************	
    public static void main (String ... args) 
    {
        try ( Connection conn = dbConnect("igolovko", "qaz123qaz");
              Statement stmt = conn.createStatement())
       {
           conn.setAutoCommit(false);
		   String tN = "state_capital";
           
			//check if table exists
			int e = exists(conn, tN);
			if (e != 0)
				System.out.println("Table " + tN + " already exists.");			
			else
			{
				System.out.println("Table " + tN + " doesn't exists.");
				System.out.println("Starts to create table.");
				stmt.executeUpdate("CREATE TABLE state_capital(ID int NOT NULL," + 
	                  				" state varchar2(20), capital varchar2(25))");
			}
				
	    	//check if table already have a value
			int c = countRows(conn, tN); 
	    	if (c != 0) 
	   	 	{
		  	  System.out.println("Table state_capital already have a data.");
	    	} 
			else
			{
				System.out.println("Now we will populate table state_capital with data " +
									"from file US_state.");
				String states[]   = new String[50];
	   	 		String capitals[] = new String[50];

				parseUSStates(states, capitals);
		
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO " +
										"state_capital VALUES (?,?,?)");
			
				for (int i =1; i <= states.length; i++)
				{
					pstmt.setInt(1, i);
					pstmt.setString(2, states[i-1]);
					pstmt.setString(3, capitals[i-1]);
					pstmt.executeUpdate();	
				}
			}

			// Select all states starts with O
			System.out.println("Execute query 'select everything from table "
								+ "where state starts with O'");
			String  query1 = "SELECT * FROM " + tN + " WHERE state lIKE 'O%'";
			displayQuery(conn, query1);
		
			// select with limit 4
			System.out.println("Execute query 'select everything from table "
								+ "for first 4 rows.");	
			String query2 = "SELECT * FROM " + tN + " WHERE ROWNUM < 5";
			displayQuery(conn, query2);
	
			// Select first 9 capitals and sort it by DESC
			System.out.println("Execute query 'Select first 9 rows and sort it by DESC");
			String query3 = "SELECT * FROM " + tN + " WHERE ROWNUM < 10" 
							 + " ORDER BY capital DESC";
			displayQuery(conn, query3);
					
			// just in case drop table;
			System.out.println("Now we will drop the table " + tN);
			stmt.executeUpdate("DROP table " + tN);
		}
   		catch (Exception e) 
   		{
         	System.err.println(e);
		}     
  	}
}
		   
		   
	/*	   
		   
		   stmt.executeUpdate("CREATE TABLE " +
                  "state_capital(ID int NOT NULL," + 
                  " state varchar2(20), capital varchar2(25))");
		   
		   int c = countRows(conn, tN); 
		   System.out.println("Output of query 'SELECT COUNT(*) FROM state_capital: " + c);
		   if (c != 0) 
		   {
			   System.out.println("Table state_capital already have a data.");
		   }
		   else
		   {
		   		System.out.println("Now we will populate table state_capital with data " +
					"from file US_state.");
				
		        String states[]   = new String[50];
		        String capitals[] = new String[50];

		        parseUSStates(states, capitals);
			
				try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO " +
							"state_capital VALUES (?,?,?)")
				{
					for (int i =1; i <= states.length; i++)
					{
						pstmt.setInt(1, i);
						pstmt.setString(2, states[i-1]);
						pstmt.setString(3, capitals[i-1]);
						pstmt.executeUpdate();	
					}
				}
			}
				
				
			// Select all states starts with O
			System.out.println("Execute query 'select everything from table "
								+ "where state starts with O'");
			displayQuery(query1);
			
			// select with limit 4
			System.out.println("Execute query 'select everything from table "
								+ "for first 3 rows.");	
			displayQuery(query2);
		
			// Select all capitals ends with s
			System.out.println("Execute query 'select id, state, capital from table "
							+ "where capital ends with s'");
			displayQuery(query3);
		}
			
	/*		System.out.println("Execute query 'select everything from table "
								+ "where state starts with O'");
			ResultSet rs = stmt.executeQuery("SELECT * FROM state_capital "
												+ " WHERE state lIKE 'O%'");
			while (rs.next())
			{
				System.out.println("ID: " + rs.getInt(1); + " state: " +
					             rs.getString(2); + " capital: " + rs.getString(3););
			}
			
			// select with limit 4
			System.out.println("Execute query 'select everything from table "
								+ "for first 3 rows.");
			rs = stmt.executeQuery("SELECT * FROM state_capital LIMIT 3");
			while (rs.next())
			{
				System.out.println("ID: " + rs.getInt(1); + " state: " +
					             rs.getString(2); + " capital: " + rs.getString(3););
			}
			
			// Select all capitals ends with s
			System.out.println("Execute query 'select state and capital from table "
								+ "where capital ends with s'");
			ResultSet rs = stmt.executeQuery("SELECT state, capital FROM state_capital "
												+ " WHERE capital lIKE '%s'");
			while (rs.next())
			{
				System.out.println("state: " + rs.getString(2); + 
								   " capital: " + rs.getString(3););
			}		
        } 
*/		
   		
