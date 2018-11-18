import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.net.*;
import java.sql.*;
import java.io.*;
import java.util.*;

/* Author: student CS 211E Irina Golovko
* Date  :   02/24/2018
* Homework assignment : #3 rmi program.
* Objective : This is a StateCapitalServer.java program,
which implements 2 methods getStates(String regex)
* and getCapitals(String regex) from StateCapitalIntf interface:
* - getStates method return String array of states
* according to the regex recieved from client; 
* - getCapitals method return String array of capitals
* according to the regex recieved from client. 
* StateCapitalServer works with table state_capital 
* in ORACLE DataBase 
/**********************************************************/

public class StateCapitalImpl extends UnicastRemoteObject
	implements StateCapitalIntf
{
    static final String UNAME = "igolovko";
	static final String PASSWD = "qaz123qaz";
    static final String OWNER = "IGOLOVKO";
	static final String TN = "STATE_CAPITAL";
	
	
	public StateCapitalImpl() throws RemoteException
	{
		super();
	}
//*****************************getCapitals**********************************	
	public String[] getCapitals(String capital_regex) throws RemoteException
	{
		ArrayList<String> result = new ArrayList<String>();
		try ( Connection conn = dbConnect(UNAME, PASSWD);
	            Statement stmt = conn.createStatement())
		{
			conn.setAutoCommit(false);
			String query = "SELECT capital FROM " + TN +
				   " WHERE regexp_like (capital, '" + capital_regex 
					   + "', 'i')";
			if (!tableExists(stmt, TN, OWNER))
			{
				// display error message on server side
				System.err.println("Server Error Message: Table " + 
					 				TN + " doesn't exist.");
				
				
				
				
				
				// for error message on client side
				result.add("Error message: Table " + TN + 
							" doesn't exist.");
			}
		   
			int count = countRows(stmt, TN);
			if (count == 0)
			{
				// display error message on server side
				System.err.println("Server Error message: Table " + TN 
									+ " is empty.");
				// for error message on client side
				result.add("Error message: Table " + TN + 
					 		" is empty.");
			}
			else
			{
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next())
				{
					result.add(rs.getString(1));
				}
			}
			if (result.isEmpty())
			{
				result.add("No capitals found with given regex");
			}
		}
		catch (SQLException e) 
		{
			System.err.println(e);
		}
		String[] capitals = result.toArray(new String[result.size()]);
		return capitals;		
	}

//*****************************getStates**********************************	
	public String[] getStates(String state_regex) throws RemoteException
	{
		ArrayList<String> result = new ArrayList<String>();
		try ( Connection conn = dbConnect(UNAME, PASSWD);
		       Statement stmt = conn.createStatement())
		{
			conn.setAutoCommit(false);
			String query = "SELECT state FROM " + TN +
					   " WHERE regexp_like (state, '" + state_regex 
						   + "', 'i')";
			if (!tableExists(stmt, TN, OWNER))
			{
				// display error message on server side
				
				
				
				
				
				System.err.println("Server Error Message: Table " + 
					 				TN + " doesn't exist.");
				// for error message on client side
				result.add("Error message: Table " + TN + 
							" doesn't exist.");
			}
		   
			int count = countRows(stmt, TN);
			if (count == 0)
			{
				// display error message on server side
				System.err.println("Server Error message: Table " + TN 
									+ " is empty.");
				// for error message on client side
				result.add("Error message: Table " + TN + 
					 		" is empty.");
			}
			else
			{
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next())
				{
					result.add(rs.getString(1));
				}
			}
			if (result.isEmpty())
			{
				result.add("No states found with given regex.");
			}
			
		}
		catch (SQLException e) 
		{
			System.err.println(e);
		}
		String[] states = result.toArray(new String[result.size()]);
		return states;		
	}	

//*****************************dbConnect*****************************************
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
		
//*****************************tableExists**************************************
// This method returns false if table doesn't exists
	public static boolean tableExists(Statement stmt, 
									  String tableName, String owner) 
	{
		boolean exist = true;
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

//*****************************countRows*****************************************
// We use this method to check if the table is empty. 
// It returns number of rows in the table
	public static int countRows(Statement stmt, String tableName) 
	{
		int count = -1;
		try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName))
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

//*****************************main***************************************
	
	
	
	
	
	
	public static void main (String ... args)
	{
		System.out.println("Launching server....");
		try
		{
			StateCapitalImpl asi = new StateCapitalImpl();
			Naming.rebind("StateCapitalServer", asi);
			
			System.out.println("StateCapitalServer is ready.");
		}
		catch (RemoteException e)
		{
			System.out.println(e);
		}
		catch (MalformedURLException e)
		{
			System.out.println(e);
		}
	}
}
