<%@ page language="java" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.Properties" %>
<html>
<head>
<title>System Properties with jsp</title>
<meta charset=UTF-8>	
</head>

<body>	
<h2>Answers</h2>
	
<%!
	String java_version = System.getProperty("java.version");
	String java_hd = System.getProperty("java.home");
	String os_name = System.getProperty("os.name");
	String os_version = System.getProperty("os.version");
	String user_name = System.getProperty("user.name");
	String homdir = System.getProperty("user.home");
	String user_cd = System.getProperty("user.dir");
	String result;
%>	

<%
String[] properties = request.getParameterValues("property");
if (properties != null) {
%>
<ul>
<%
    for (int i = 0; i < properties.length; i++) 
 	{
		String choice = properties[i];		
		if (choice.equals("java version"))
			result = "java version: " + java_version;
		if (choice.equals("java home dir"))
			result = "java home directory: " + java_hd;				  
		if (choice.equals("os name"))
			result = "os name: " + os_name +  
 			   " version: " + os_version;
		if (choice.equals("user name"))
			result = "user name: " + user_name;	
		if (choice.equals("user home dir"))
			result = "user home directory: " + homdir;	
		if (choice.equals("user current dir"))
			result = "user current directory: " + user_cd;						
%>
<li><%= result %></li>			
<%
    }
%>
</ul>
<br>
<%
}
%>
</body>
</html>




