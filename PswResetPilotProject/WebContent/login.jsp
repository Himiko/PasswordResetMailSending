<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Login Page</title>
</head>
<body>
	<h2>User Login</h2>
	<font color="red"><s:actionerror/></font>
	<s:form action="login"  method="post">  
	   <s:textfield name="userName" label="UserName"></s:textfield>  
		<s:password name="password" label="Password"></s:password>
		<s:submit value="login"></s:submit>
	</s:form>
	<p class="margin-left:12px;"><a href="reset.jsp">Forgot your password?</a></p>
	<br>
	<hr>
	<a href="register.jsp">Go to Register</a>
</body>
</html>