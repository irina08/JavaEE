import java.rmi.*;
import java.rmi.server.*;
import java.net.*;
import java.util.*;

/* Author: student CS 211E Irina Golovko
* Date  :   02/24/2018
* Homework assignment : #3 rmi program.
* Objective : This client program StateCapitalClient
* connects to the StateCapitalServer with IPAddress 
* 144.147.1.42, using rmi in order to get information 
* about states or capitals of states of the USA using 
* regex from table state_capital in ORACLE DataBase.
* Return: - array of states or capitals if regex 
* match some states or capitals in the table;
* - "No states (or capitals) found with given
* regex" if array is empty; 
* - array with error message if table doesn't exist:
* Error message: Table STATE_CAPITAL does not exist.
* - array with error message if table empty:
* Error message: Table STATE_CAPITAL is empty.
/**********************************************************/

public class StateCapitalClient
{
	public static void main (String ... args)
	{
		if (args.length == 0)
		{
			System.err.println("You should to put in the "
				 	+ "command line after the name of program"
					+ " Domain Name or IPAddress of the Server.");
			System.exit(1);
		}
		try 
		{
			String url = "rmi://" + args[0] + "/StateCapitalServer";
			StateCapitalIntf asi = (StateCapitalIntf) Naming.lookup(url);
			
			System.out.println("Print states, which have "  
						+ "double 'n' or 'm' characters.");
			String[] states1 = asi.getStates("([ns])\\1");
			for (String st1 : states1)
			{
				System.out.println(st1);
			}
			
			System.out.println("Print states, which start " 
								+ "with 'a', have 0 or more any other "
								+ "characters and end with 'a'");
			String[] states2 = asi.getStates("^a.*a$");
			
			
			
			
			
			
			
			for (String st2 : states2)
			{
				System.out.println(st2);
			}
			
			System.out.println("Print states, which start " 
								+ "with 'b'");
			String[] states3 = asi.getStates("^b");
			for (String st3 : states3)
			{
				System.out.println(st3);
			}
			
			System.out.println("Print capitals have "
			           + "names consist of only 2 words"
			           + " divided by space.");
			//not included capital - Salt Lake City
			String[] capitals1 = asi.getCapitals("^[a-z]+\\s[a-z]+$");
			for (String cap1 : capitals1)
			{
				System.out.println(cap1);
			}
			
			System.out.println("Print capitals with "
			             + "names with 2 and more words splited by "
			             + "space and first word has only 3 characters.");
			String[] capitals2 = asi.getCapitals("^[a-z]{3}\\s[a-z]+");
			for (String cap2 : capitals2)
			{
				System.out.println(cap2);
			}
			
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
