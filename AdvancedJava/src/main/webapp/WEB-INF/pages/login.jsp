<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" />
</head>
<body>
	
	<div class="login">
		<h1>Login</h1>
	<form action="login" method="POST">
		<div class="textfield">
			<label for="user_email">Email or Username:</label> <input type="text"
				id="user_name" placeholder="" name="user_email">
		</div>

		<div class= "textfield">
			<label for="user_password">Password:</label> <input type="password"
				id="user_password" placeholder="" name="user_password" >
		</div>
		<div class="textfield">
			<label> <input type="checkbox" name="remember_me">
				Remember me
			</label>
		</div>

		<button type="submit">Sign In</button>
	

	<div class="register-link">
		Don't have an account? <a href="register">Create account</a>
	
		<a href="#">Forgot Password?</a>
	</div>
	</form>
	</div>
	<div class="error-message">
    <%
        String error = (String) request.getAttribute("error");
        if (error != null && !error.isEmpty()) {
    %>
        <p style="color: red;"><%= error %></p>
    <%
        }
    %>
    </div>

</body>
</html>