<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>  
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">  
	<title>Welcome</title>  
</head>  
<body>
	<s:set name="page" value="pageCheck" />
	<s:if test="%{#page == 'loginPage'}">
		Welcome, <s:property value="userName" />  <br>
		<s:a href="login.jsp">Go to Login</s:a>
	</s:if>
	<s:else>
		You are successfully registered! <br>
		<s:a href="login.jsp">Go to Login</s:a>
	</s:else>
</body>

