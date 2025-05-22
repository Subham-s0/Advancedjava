<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.Advancedjava.util.Sessionutil" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nestaway1</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/Adminprofile.css" />
</head>
<body>

<jsp:include page="/WEB-INF/pages/admin/Adminheader.jsp" />
<div class ="main-content">
 <div class="container">
 
<div class="content-area">
			
				<h2>Personal Information</h2>
				<%
				String error = (String) request.getAttribute("error");
				%>
				<div
					class="error-message <%=(error != null && !error.isEmpty()) ? "visible" : ""%>">
					<c:if test="${not empty error}">
						<p style="color: red;">
							<c:out value="${error}" />
						</p>
					</c:if>

				</div>
				<%
				String success = (String) request.getAttribute("success");
				%>
				<div
					class="success-message <%=(success != null && !success.isEmpty()) ? "visible" : ""%>">
					<%
					if (success != null && !success.isEmpty()) {
					%>
					<p style="color: #256029;"><%=success%></p>
					<%
					session.removeAttribute("success");
					%>
					<%
					}
					%>
				</div>
				<form class="profile-form"
					action="${pageContext.request.contextPath}/adminprofile" method="POST"
					enctype="multipart/form-data">
					<input type="hidden" name="formType" value="profileUpdate" />
					<div class="form-row">
						<div class="form-group">
							<label for="firstName">First Name</label> <input type="text"
								id="firstName" name="firstName"
								value="${Current_user.userFirstName}">

						</div>
						<div class="form-group">
							<label for="lastName">Last Name</label> <input type="text"
								id="lastName" name="lastName"
								value="${Current_user.userLastName}">

						</div>
					</div>

					<div class="form-row">
						<div class="form-group">
							<label for="username">Username</label> <input type="text"
								id="username" name="username" value="${Current_user.userName}">

						</div>
						<div class="form-group">
							<label>Gender</label>
							<div class="radio-group">
								<label class="radio-label"> <input type="radio"
									id="gender-male" name="gender" value="male"
									<c:if test="${Current_user.gender == 'male'}">checked</c:if>>
									<span>Male</span>
								</label> <label class="radio-label"> <input type="radio"
									id="gender-female" name="gender" value="female"
									<c:if test="${Current_user.gender == 'female'}">checked</c:if>>
									<span>Female</span>
								</label>
							</div>
						</div>
					</div>

					<div class="form-row">
						<div class="form-group">
							<label for="dob">Date of Birth</label> <input type="date"
								id="dob" name="dob" value="${Current_user.userDob}">
						</div>
						<div class="form-group">
							<label for="email">Email Address</label> <input type="email"
								id="email" name="email" value="${Current_user.userEmail}">
						</div>
					</div>

					<div class="form-row">
						<div class="form-group">
							<label for="phone">Phone Number</label> <input type="tel"
								id="phone" name="phone" value="${Current_user.userPhnNo}">

						</div>
						<div class="form-group img">
							<label for="profile_picture">Update Profile Picture</label> <input
								type="file" id="image_file" name="image_file" accept="image/*">
						</div>

					</div>

					<div class="form-actions">
						<button type="submit" class="btn-primary">Save Changes</button>
						<button type="reset" class="btn-reset">Reset</button>
					</div>
				</form>
				<form class="profile-form "
					action="${pageContext.request.contextPath}/adminprofile" method="POST">
					<input type="hidden" name="formType" value="ChangePassword" />
					<div class="form-row">
						<div class="form-group">
							<label for="currentPassword">Current Password</label> <input
								type="password" id="currentPassword" name="currentPassword">
						</div>
					</div>
					<div class="form-row">
						<div class="form-group">
							<label for="newPassword">New Password</label> <input
								type="password" id="newPassword" name="newPassword">
						</div>
						<div class="form-group">
							<label for="confirmPassword">Confirm New Password</label> <input
								type="password" id="confirmPassword" name="confirmPassword">
						</div>
					</div>
					<div class="form-actions">
						<button type="submit" class="btn-primary">Update Password</button>
					</div>
				</form>
		

</div>
 </div>
  </div>
</body>

</html>