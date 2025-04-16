<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/register.css" />
</head>
<body>
    <div class="registration_form">
        <h1>Create Account</h1>
         <%String error = (String) request.getAttribute("error"); %>
         <div class="error-message <%= (error != null && !error.isEmpty()) ? "visible" : "" %>">
		    <%
		       
		        if (error != null && !error.isEmpty()) {
		    %>
		        <p style="color: red;"><%= error %></p>
		    <%
		        }
		    %>
    </div>
        <form action="register" method="POST">
        	<div class="form_row">
	        	<div class="textfield">
	                <label for="user_firstname">First Name:</label>
	                <input type="text" id="user_firstname" name="user_firstname" value="<%= request.getAttribute("user_firstname") != null ? request.getAttribute("user_firstname") : "" %>" maxlength="20">
	            </div>
	        	
	            <div class="textfield">
	                <label for="user_lastname">Last Name:</label>
	                <input type="text" id="user_lastname" name="user_lastname" value="<%= request.getAttribute("user_lastname") != null ? request.getAttribute("user_lastname") : "" %>" maxlength="20">
	            </div>
            </div>
            <div class="form_row">
	        	
	            <div class="textfield">
	                <label for="user_email">Email:</label>
	                <input type="email" id="user_email" name="user_email" value="<%= request.getAttribute("user_email") != null ? request.getAttribute("user_email") : "" %>" maxlength="50">
	            
	            </div>
	             <div class="textfield">
                <label for="user_phnno">Phone Number:</label>
                <input type="tel" id="user_phnno" name="user_phnno" value="<%= request.getAttribute("user_phnno") != null ? request.getAttribute("user_phnno") : "" %>" maxlength="10" >
           		</div>
		        </div>
            
            <div class="form_row">
            <div class="textfield">
                <label for="user_name">Username :</label>
                <input type="text" id="user_name" name="user_name" value="<%= request.getAttribute("user_name") != null ? request.getAttribute("user_name") : "" %>" maxlength="50">
            </div>

            <div class="textfield">
                <label for="user_dob">Date of Birth:</label>
                <input type="date" id="user_dob" name="user_dob" value = "<%= request.getAttribute("user_dob") != null ? request.getAttribute("user_dob") : "" %>" maxlength="50">
            </div>
            </div>           

	            <div class="textfield">
	                <label for="user_password">Password:</label>
	                <input type="password" id="user_password" name="user_password" maxlength="30">
	            </div>
	            <div class="textfield">
	                <label for="confirm_password">Confirm password:</label>
	                <input type="password" id="confirm_password" name="confirm_password" maxlength="30">
	            </div>
				
            <button type="submit">Register</button>
        </form>
        <div class="login-link">
            Already have an account? <a href="login">Login here</a>
        </div>
    </div>
   
</body>
</html>
