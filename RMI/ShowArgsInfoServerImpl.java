import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.net.*;
import java.util.*;

/**
   Author       : student Irina Golovko
   Date         : 04/23/2018
   Quiz : # 2 - show passed arguments to the method from 
				the client and data types of passed arguments
	 			using rmi
   Course       : CS211E 
   Objective    : This is server side ShowArgsInfoServerImpl.java 
				program which implemets 2 methods from 
				remote interface ShowArgsInfoIntf:
				- String[] writeln(Object ... array) - return
				String array of passed arguments into the method 
				from client;
	 			- String[] type(Object ... array) - return
				String array of passed arguments into the method 
				from client with data type. (for example, output: 
				12: Integer; dog: String).				
 	 			If no one argument passed into the methods, 
				both of methods return message "No one argument 
				passed into the given method". 
 	 			This server program may be invoked using rmiregistry on port 23000:
				rmiregistry 23000 & (for Unix/Linux)
*/

public class ShowArgsInfoServerImpl extends UnicastRemoteObject
	implements ShowArgsInfoIntf
{
//***************************constructor*****************************	
    public ShowArgsInfoServerImpl() throws RemoteException
    {
         super();
    }
	
//**************************writeln method****************************
/* 
   In both methods we use varargs feature which can handle an arbitrary 
   number of parameters automatically - using the array. In our case we 
   use array of Object. It can handle objects with different data types. 
   8 Primitive data types like byte, int, short, double, float, long, 
   char, boolean will be automatically converted to the wrapper 
   classes (Byte, Integer, Short, Double, Float, Long, Character, 
   Boolean). Wrapper classes inherite from java.lang.Object class, and 
   we can find wrapper class data type of passed argument. 
*/		
	
	
	
	
	
	
	
	
	public String[] writeln(Object ... array) throws RemoteException
	{		
		ArrayList<String> list = new ArrayList<String>();
		if (array.length > 0)
		{
			for (int i=0; i < array.length; i++)
			{
				list.add(array[i].toString());
			}			
		}
		else
			list.add("No one argument passed " +
				"into the given method.");
		return (list.toArray(new String[list.size()]));
	}
//************************type method****************************		 
    public String[] type(Object ... array) throws RemoteException
	{
		ArrayList<String> list = new ArrayList<String>();
		if (array.length > 0)
		{
			for (int i=0; i < array.length; i++)
			{
				list.add(array[i] + ": " + 
					array[i].getClass().getSimpleName());		
			}
		}
		else
			list.add("No one argument passed " +
				"into the given method.");
		return (list.toArray(new String[list.size()]));
	}
//**************************main method******************************		 
     public static void main (String ... args)
     {
		 System.out.println("Launching server....");
		 try
		 {
		     ShowArgsInfoServerImpl asi = new ShowArgsInfoServerImpl();
			 Registry registry = LocateRegistry.getRegistry(23000);
			 registry.rebind("ShowArgsInfoServer", asi);
             System.out.println("ShowArgsInfoServer is ready.");
        }
        catch (RemoteException e)
        {
            System.out.println(e);
        }
    }
}
