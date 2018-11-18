import java.rmi.*;
import java.net.*;
import java.rmi.server.*;

/**
   Author       : student Irina Golovko
   Date         : 04/23/2018
   Quiz : # 2 - show on client side passed arguments to the method from 
				the client and data types of passed arguments
	 			using rmiregistry 
   Course       : CS211E 
   Objective    : This is client side ShowArgsInfoClient.java program 
				connects to the ShowArgsInfoServer with IPAddress 
				144.147.1.42 or Domain Name dunes.ccsf.edu. Server  
				is invoked using rmiregistry with port number 23000.
 				And this program uses ShowArgsInfoIntf to find the 
 				implementation of remote methods 
				String[] writeln(Object ... array) and
				String[] type(Object ... array), where client would 
 				like to pass arguments.
	 			Method String[] writeln(Object ... array) return
				String array of passed arguments into the method 
				from client; 
	 			Method String[] type(Object ... array) - return
				String array of passed arguments into the method 
				from client with data type of arguments.
 				Both of method will be automatically converted 
				primitive data types to Wrapper classes and method
 				type() will return data types for Wrapper classes
 				of primitive data types.
 	 			If no one argument passed into the method, 
				both of methods return message "No one argument 
				passed into the given method" 
*/

public class ShowArgsInfoClient
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
			String url = "rmi://" + args[0] + ":23000/ShowArgsInfoServer";
			ShowArgsInfoIntf asi = (ShowArgsInfoIntf) Naming.lookup(url);
			
			
			
			
			
			System.out.println("Usage of method writeln(12, 123.24, 12.345f," +
					 			" 'dog', 'H', new StringBuilder('cat'), true):");
			String[] ar1 = asi.writeln(12, 123.24, 12.345f, "dog", 'H', 
										new StringBuilder("cat"), true);
			for (String st1 : ar1)
			{
				System.out.println(st1);
			}
			
            System.out.println("Usage of method writeln():");
			String[] ar2 = asi.writeln();
			for (String st2 : ar2)
			{
				System.out.println(st2);
			}
			
			System.out.println("--------------------------------------------");
			
            System.out.println("Usage of method type(1, 12.47, 12.345f, 'D'," +
				 			" 'cat', new StringBuilder('good'), false):");
			String[] ar3 = asi.type(1, 12.47, 12.345f, 'D', "cat", 
									new StringBuilder("good"), false);
			for (String st3 : ar3)
			{
				System.out.println(st3);
			}
			
            System.out.println("Usage of method type():");
			String[] ar4 = asi.type();
			for (String st4 : ar4)
			{
				System.out.println(st4);
			}
        }
        catch (Exception e)
       {
            System.out.println(e);
       }
    }
}
