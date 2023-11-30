<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration Form</title>
</head>
<body>
	<%@ include file="header.jsp" %>
	<h2> Registration Form</h2>
	<form action="RegistrationServlet" method="post">
		Name: <input type="text" name="name" value="<%=
		request.getParameter("name") %>"><br>
		Email: <input type="text" name="email" value="<%=
		request.getParameter("email") %>"><br>
		Password: <input type="password" name="password"><br>
		<input type="submit" value="Register">
	</form>


</body>
</html>