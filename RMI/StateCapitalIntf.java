import java.rmi.*;

/* Author: student CS 211E Irina Golovko
* Date  :   02/24/2018
* Homework assignment : #3  rmi program.
* Objective : This is a StateCapitalIntf.java program,
which create  StateCapitalIntf Interface with 2 methods: 
* String[] getStates(String regex) and 
* String[] getCapitals(String regex).
/**********************************************************/

public interface StateCapitalIntf extends Remote
{
	
/** Show all states with the names match 
	the regex recieved from client.
	@param state_regex  The 'regex' from the client.
	@return  String array of states, 
	         if no match return String array with error 
			 message "No states found with given regex" */
	
	String[] getStates(String state_regex) throws RemoteException;
	
	
	/** Show all capitals with the names 
		match the regex recieved from client.
		@param capital_regex  The 'regex' from the client.
		@return  String array of capitals, 
		        if no match found return String array with 
				error message "No capitals found
				with given regex" */
	
	String[] getCapitals(String capital_regex) throws RemoteException;
}
