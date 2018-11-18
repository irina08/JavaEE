<%--
   Author       : student CS211E Irina Golovko
   Date         : 04/15/2018
   Homework assignment : # 5 - properties.jsp
   Course       : CS211E 
   Objective    : User can chose  one or more options:
				  - Show java version - it
				  will return java version on user's OS
 				  - Show java home directory - it
				  will return location of java 'jre' on user's OS
				  - Show operating system name and version -
				  it will return system name
 				  and system version of user's os. 
				  - Show user name -  
 				  it will return the user name on user's os
				  - Show user home directory - it will return 
 				  user's home directory on user's os
 				  - Show user current director - it will return 
 				  in which directory user now on his os. 
 				  Answers will appeare on the same page.
--%>

<html lang="en">
<head>
<title>System Properties with jsp</title>
<meta charset=UTF-8>	
<style type="text/css">
body {background-color:#e6fff5;}	
#content {margin: 0px auto; width: 450px; border: 0px; 
padding: 5px 5px 5px 5px; text-align: left;}
#btn {margin-top: 25px; text-align: center;font-size: 20px; 
font-weight: bold;}
.hd2 {color: blue; font-size: 25px;}
.tab {padding: 3px; font-size: 20px;
color: green; font-weight: bold;}
a {color: black; font-size: 18px; font-weight: bold;
text-decoration: none;}
ul {list-style-type: none; margin: 0; padding: 0;}
</style>	
</head>

<body>
<section id="content">	
<h2 class="hd2">What properties about your operating 
system do you want to know:</h2>
<form method="post">
<table>
<tr><td class="tab">
<input type="checkbox" name="property" value="java version">
Show java version<br>




</td></tr>
<tr><td class="tab">
<input type="checkbox" name="property" value="java home dir">
Show java home directory
</td></tr>
<tr><td class="tab">
<input type="checkbox" name="property" value="os name">
Show operating system name and version
</td></tr>
<tr><td class="tab">
<input type="checkbox" name="property" value="user name">
Show user name
</td></tr>
<tr><td class="tab">
<input type="checkbox" name="property" value="user home dir">
Show user home directory
</td></tr>
<tr><td class="tab">
<input type="checkbox" name="property" value="user current dir">
Show user current directory
</td></tr>
</table>

<input id="btn" type="submit" value="Submit">
</form>

<%@ page import="java.util.Properties" %>

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
<h2 class="hd2">Result: </h2>
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
<li class="tab"><%= result %></li>			
<%
    }
%>
</ul>
<br>
<a href="<%= request.getRequestURI() %>">BACK</a>
<%
}
%>

</section>
</body>
</html>




