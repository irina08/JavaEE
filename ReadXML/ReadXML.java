import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.io.File;

/* Author: student CS 211E Irina Golovko
* Date  :   05/14/2018
* Homework assignment : extra credit #1 Parse created XML file 
* Objective : This program will parse prop.xml file using DOM Parser.
* prop.xml file contains all java system properties 
* for using OS with next format:
* <?xml version="1.0" encoding="UTF-8" standalone="no"?>
* <Java_System_Properties>
*     <property name="java_runtime_name">
*		 Java(TM) SE Runtime Environment</property>
* ..........
* 	  <property name="sun_cpu_isalist"/>
*</Java_System_Properties>
* It will reproduce outputs with following format:
* Root element: Java_System_Properties
* --------------------------
* Current Element: property
* property name: java_runtime_name
* Value: Java(TM) SE Runtime Environment
* --------------------------
* Current Element: property
------
/**********************************************************/

public class ReadXML
{
	public static void main(String ... args) 
	{
		try 
		{
			File file = new File("/Users/irinagolovko/Desktop/prop.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			System.out.println("--------------------------"); 
			
			NodeList nList = doc.getElementsByTagName("property");			
			
			for (int i = 0; i < nList.getLength(); i++) 
			{
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{
					
					
					
					
					
					System.out.println("Current Element: " + nNode.getNodeName());
					if (nNode.hasAttributes()) 
					{
						// get attributes values - names of properties
						// in our case we know that element has only one 
						// attribute "name" - name of property, that's why 
						// we can display first value of attribute 
						// of element than only value of element
						NamedNodeMap nMap = nNode.getAttributes();
						for (int s = 0; s < nMap.getLength(); s++)
						{
							Node tempN = nMap.item(s);
							System.out.println("property name: " + 
								tempN.getNodeValue());
			            }
					}  
					System.out.println("Value: " + nNode.getTextContent());
					System.out.println("--------------------------"); 
				}
			}	
		}	
									
		catch (Exception e) 
		{
			e.printStackTrace();
    	}
	}
}