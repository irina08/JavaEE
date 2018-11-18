/*
***************************************************************
Author    : student CS 211E Irina Golovko
Date      : 03/24/2018
Homework assignment : # 4 - USStateCapitalServlet.java
Html File : usa.html
Objective : this Servlet program works with Tomcat and jdbc 
			with mySql db, located on my OS 
 			(or with Oracle db located on dunes.ccsf.edu).
 			User can input state or capital of state of the USA 
 			using usa.html file, and then user push button 
 			"Submit". It will display the answer:
 			if you put state it will return capital,
 	 		if you put capital it will return state,
 			if input doesn't match any state or capital 
 			it will return error message.

 			To run this program from your machine you need: 
 	 		download and install Tomcat on your machine;
			make sure that $JAVA_HOME and $CATALINA_HOME
 			is in your PATH;
 			- for mySql db: place mySql connector mysql-connector-java-5.1.46-bin.jar
 	 		file under the $CATALINA_HOME/lib or you can 
 			put mysql-connector-java-5.1.46-bin.jar and servlet-api.jar
			under /Library/Java/Extensions
 			- for Oracle db: place Oracle connector ojdbc7.jar under 
 			the $CATALINA_HOME/lib or you can put ojdbc7.jar 
  			and servlet-api.jar under /Library/Java/Extensions
 	 		
 			create next directories: 
			- $CATALINA_HOME/webapps/USA1
 			place here usa.html file, pitures which you use with html;
			- $CATALINA_HOME/webapps/USA1/WEB-INF and
 			place here web.xml and my.prop file 
 			with user name, url, password to access data base;
 			- $CATALINA_HOME/webapps/USA/WEB-INF/classes and
 			place here USStateCapitalServlet.java and .class file;
 			- startup Tomcat: $CATALINA_HOME/bin/startup.sh (for Linux/Unix);
 			run program in the web browser:
 			http://localhost:8080/USA1/usa.html
 			shutdown Tomcat: $CATALINA_HOME/bin/startup.sh (for Linux/Unix);

  			If you put url like this: jdbc:mysql://IPAddres_or_DomainName_ 
 			where_db_islocated:3306/nameOfDB, you can get warning message: 
 			"Establishing SSL connection without server's identity 
 			verification is not recommended. According to MySQL 5.5.45+ 
 			and up requirements SSL connection must be established by default 
 			if explicit option isn't set. For compliance with existing applications 







 			not using SSL the verifyServerCertificate property is set to 'false'. 
			You need either to explicitly disable SSL by setting useSSL=false, or set
	 		useSSL=true and provide truststore for server certificate verification.
 			Thats why I added to the end of URL after the name of db without 
 			spaces "?useSSL=false". 
  			url=jdbc:mysql://localhost:3306/sonoo?useSSL=false
******************************************************************/

import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class USStateCapitalServlet extends HttpServlet
{
	private static String url, userN, passwd;
	private static final String TABLEN = "STATE_CAPITAL";

/******************************* doPost() *******************************/
	public void doPost(HttpServletRequest req, HttpServletResponse res)
                       throws ServletException, IOException
	{
		Connection conn = null;
		PrintWriter pw = null;
		Statement stmt = null;
		
		res.setContentType("text/html");
		pw = res.getWriter();

		pw.println("<html><head>");
		pw.println("<title>Response of Servlet with jdbc</title>");
		pw.println("<meta charset=UTF-8>");
		pw.println("<style type=text/css>");
		pw.println("body {background-color:#e6fff5;}");
		pw.println("img.left {float: left;}");
		pw.println("#err {color: red; text-align: center; font-size: 40px;}");
		pw.println("#hd2 {color: blue; text-align: center; font-size: 40px;}");	
		pw.println("#hd3 {color: green; text-align: center; font-size: 35px;}");
		pw.println("</style></head><body>");
		pw.println("<br><br><br>");
		pw.println("<img src='map.jpg' class='left' alt='Map' width='450'>");

		try
		{
	       url = getProperty("url");
	       userN = getProperty("userN");
		   passwd = getProperty("passwd");
			
			
			
			
			
			
			
			//For mySQL db
			Class.forName("com.mysql.jdbc.Driver");
			//For Oracle db
			// Class.forName("oracle.jdbc.driver.OracleDriver");  
			conn = DriverManager.getConnection(url, userN, passwd);
			stmt = conn.createStatement();
			String name = req.getParameter("user_input");
			ResultSet rs = stmt.executeQuery("select capital from " + TABLEN + 
			 						" where state = '" + name +"'");
			pw.println("<h3 id='hd3'>Thank you for your request</h3>");
		 
			if(rs.next())
				pw.println("<h2 id='hd2'>The Capital of " + name 
					+ " is: " + rs.getString(1) + "</h2>");
			else
			{
				rs = stmt.executeQuery("select state from " + TABLEN +
					 " where capital = '" + name + "'");

				if(rs.next())
					pw.println("<h2 id='hd2'>The state of " + name + 
						" is: " + rs.getString(1) + "</h2>");
				else  
					pw.println("<h2 id='err'>" + "No such state or " + 
						"capital in the USA: " + name + "</h2>");
			}
			pw.println("</body></html>");
		}
		catch(Exception e)
		{
			pw.println("<h3 id='err'>Exception: "+ e.getMessage() + "</h3>");
	  	}   
		 finally 
		 {
			 pw.close();
			 try 
			 {
				 if (stmt != null) stmt.close();
				 if (conn != null) conn.close();
			 } 
			 catch (Exception e)
			 {
				 e.printStackTrace();
			 } 
		 } 
   }
   
   
   
   
   
   
   
/*********************** getProperty() ******************************/
   public static String getProperty(String key)
   {
      Properties p = new Properties();
      InputStream is = null;
	  
      try
      {
	  	  //For mySQL db 'my.prop' file
	      //For Oracle db "myOracle.prop" file
		  File f = new File("/Library/Tomcat/webapps/USA1/WEB-INF/my.prop");
          is = new FileInputStream( f );
      }   
	  catch ( Exception e ) 
	  { 
		  is = null; 
	  }
      try
      {
          if ( is == null ) System.exit(1);
          // Try loading properties from the file (if found)
          p.load( is );
      }   
	  catch ( Exception e ) 
	  { 
		  System.err.println(e); 
	  }
      return(p.getProperty(key));
   }  
   
}