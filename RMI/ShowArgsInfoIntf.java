import java.rmi.*;

/**
   Author       : student Irina Golovko
   Date         : 04/23/2018
   Quiz : # 2 - show on client side passed arguments to the method from 
				the client and data types of passed arguments
	 			using rmi
   Course       : CS211E 
   Objective    : a remote interface ShowArgsInfoIntf declares 
				methods: String[] writeln(Object ... array) and
	 			String[] type(Object ... array) that supposed 
				to be implemented in the ShowArgsInfoImpl 
 				and may be invoked using rmiregistry.
*/

public interface ShowArgsInfoIntf extends Remote
{	 
/** This method will accept any number of arguments 
	with different data types, recieved from client 
	and return passed arguments.
	@param varargs: data type - Object, variable name - array
	@return  String array of passed arguments into 
	 		 the method from client, if no one 
	 		 argument passed return String array 
	 		 with message "No one argument passed 
	 		 into the given method" */	
		
	String[] writeln(Object ... array) throws RemoteException;
	
/** This method will accept any number of arguments 
	with different data types, recieved from client, 
	and return passed arguments with data types.
	@param varargs: data type - Object, variable name - array
	@return  String array of passed arguments into 
		 	 the method from client with data type, if no one 
		     argument passed return String array 
		     with message "No one argument passed 
		 	 into the given method" */	
	String[] type(Object ... array) throws RemoteException;
}
