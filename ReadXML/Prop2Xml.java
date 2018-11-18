import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

/* Author: student CS 211E Irina Golovko
* Date  :   05/14/2018
* Homework assignment : extra credit #1 Create XML file prop.xml
* with java system properties for using OS - 
* first approach - Prop2Xml.java: 
* Objective : This program will create XML file using DOM Parser.
* XML file will contain all java system properties 
* for using OS with next format:
* <?xml version="1.0" encoding="UTF-8" standalone="no"?>
* <Java_System_Properties>
*   <property name="java_runtime_name">
	Java(TM) SE Runtime Environment</property>
*   <property name="java_vm_version">25.73-b02</property>
* ...............
* 	<property name="sun_cpu_isalist"/>
* </Java_System_Properties>
* where root element - Java_System_Properties
* child elements: for every java sytem property  
* element - property with attribute - name and value - 
* name of java system property. 
/**********************************************************/

public class Prop2Xml 
{
	public static void main(String ... args) 
	{
		try 
		{
			DocumentBuilderFactory dbf = 
				DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();
			// root element
			Element root = doc.createElement("Java_System_Properties");
			doc.appendChild(root);





			Properties p = System.getProperties();
			Enumeration keys = p.keys();
	
			// element "property" with attribute "name"
			// for each java system property
			Element pr = doc.createElement("property");
			Attr attrType = doc.createAttribute("name");
		 
			while (keys.hasMoreElements()) 
			{
				String key = (String) keys.nextElement();
				
				//String key1 = key.replaceAll("\\.", "_");
				String key1 = key.replaceAll(Pattern.quote("."), "_");
				
				attrType.setValue(key1);
				pr.setAttributeNode(attrType);
			 
				String value =(String) p.getProperty(key);
				pr.appendChild(doc.createTextNode(value));
				root.appendChild(pr);
				
				pr = doc.createElement("property");
				attrType = doc.createAttribute("name");
			}	

			// write the content into xml file 
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
		 
			//setup the indent property and the indent-amount
			// in order to have the pretty printing format activated
			t.setOutputProperty(OutputKeys.INDENT, "yes"); 
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(doc);
			StreamResult result = 
				new StreamResult(new File("/Users/irinagolovko/Desktop/prop.xml"));
			t.transform(source, result);
         
			// Output to console for testing
			StreamResult conRes = new StreamResult(System.out);
			t.transform(source, conRes);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
	 	}
	}
}