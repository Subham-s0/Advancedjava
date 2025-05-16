<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NestAway</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/profile.css" />
</head>
<body>
	<jsp:include page="header.jsp" />
	<div class="main-container">
		<aside class="sidebar">
			<div class="user-info">
				<div class="user-avatar">
					<img id="profileImage"
						src="${pageContext.request.contextPath}/Profile_pictureservlet"
						alt="User avatar">
					<button class="photo-edit-btn" id="editPhotoBtn"
						aria-label="Edit profile photo" type="submit">
						<svg width="16" height="16" viewBox="0 0 24 24" fill="none"
							stroke="currentColor" stroke-width="2" stroke-linecap="round"
							stroke-linejoin="round">
                            <path
								d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z" />
                            <circle cx="12" cy="13" r="4" />
                        </svg>
					</button>
				</div>
				<h3>${Current_user.userFirstName}${Current_user.userLastName}</h3>
				<p>${Current_user.userEmail}</p>
			</div>
			<nav class="sidebar-nav">
				<a href="#personal-info" class="nav-item active">Personal
					Information</a> <a href="#bookings" class="nav-item">My Bookings</a> <a
					href="${pageContext.request.contextPath}/WishListController"
					class="nav-item">My Wishlist</a> <a href="#change-password"
					class="nav-item">Change Password</a> <a
					href="${pageContext.request.contextPath}/logout"
					class="nav-item logout">Logout<i data-lucide="log-out"></i></a>
			</nav>
		</aside>

		<div class="content-area">
			<section id="personal-info" class="content-section ">
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
					action="${pageContext.request.contextPath}/Profile" method="POST"
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
			</section>

			<section id="bookings" class="content-section">
				<h2>My Bookings</h2>
				<div class="bookings-list">
					<div class="booking-card">
						<img
							src="https://images.pexels.com/photos/1488327/pexels-photo-1488327.jpeg?auto=compress&cs=tinysrgb&w=600"
							alt="Property">
						<div class="booking-details">
							<h3>Luxury Beach Villa</h3>
							<p class="location">Miami, Florida</p>
							<div class="booking-dates">
								<span>Check-in: Mar 15, 2024</span> <span>Check-out: Mar
									20, 2024</span>
							</div>
							<div class="booking-status confirmed">Confirmed</div>
						</div>
					</div>

					<div class="booking-card">
						<img
							src="https://images.pexels.com/photos/1428348/pexels-photo-1428348.jpeg?auto=compress&cs=tinysrgb&w=600"
							alt="Property">
						<div class="booking-details">
							<h3>Mountain Cabin</h3>
							<p class="location">Aspen, Colorado</p>
							<div class="booking-dates">
								<span>Check-in: Apr 10, 2024</span> <span>Check-out: Apr
									15, 2024</span>
							</div>
							<div class="booking-status pending">Pending</div>
						</div>
					</div>
				</div>
			</section>

			<section id="wishlist" class="content-section">
				<div class="hotel-grid">
					<jsp:include page="/WEB-INF/pages/Property-grid.jsp" />
				</div>
			</section>

			<section id="change-password" class="content-section ">
				<h2>Change Password</h2>
				<%
				String errorPassword = (String) request.getAttribute("errorPassword");
				%>
				<div
					class="error-message <%=(errorPassword != null && !errorPassword.isEmpty()) ? "visible" : ""%>">
					<c:if test="${not empty errorPassword}">
						<p style="color: red;">
							<c:out value="${errorPassword}" />
						</p>
					</c:if>

				</div>
				<div class="success-message">
					<%
					String successPassword = (String) request.getAttribute("successPassword");
					if (successPassword != null && !successPassword.isEmpty()) {
						request.removeAttribute("successPassword"); // Remove from request after showing (optional, but good practice for single-display messages)
					%>
					<p style="color: green; font-weight: bold;"><%=successPassword%></p>
					<%
					}
					%>
				</div>
				<form class="profile-form "
					action="${pageContext.request.contextPath}/Profile" method="POST">
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
			</section>
		</div>
	</div>
	<script>
    document.getElementById("resetProfileBtn").addEventListener("click", function () {
        window.location.href = window.location.pathname;  // This will call doGet() and reload original data
    });
</script>

	<!-- Make sure this is set in your JSP above the script -->
	<c:set var="openSection"
		value="${openSection != null ? openSection : ''}" />

	<script>
  lucide.createIcons();
  document.addEventListener('DOMContentLoaded', function() {
    const navItems = document.querySelectorAll('.sidebar-nav .nav-item:not(.logout)');
    const contentSections = document.querySelectorAll('.content-section');

    // Read the JSP-provided openSection
    const openSection = "${openSection}";

    function showSection(id) {
      contentSections.forEach(section => {
        section.style.display = (section.id === id) ? 'block' : 'none';
      });
    }

    function activateNavItem(item) {
      navItems.forEach(nav => {
        nav.classList.toggle('active', nav === item);
      });
    }

    function goToSection(id) {
      // Show the section
      showSection(id);

      // Activate its nav link
      const navLink = document.querySelector(`.sidebar-nav a[href="#${id}"]`);
      if (navLink) activateNavItem(navLink);

      // Update the URL hash
      history.replaceState(null, null, `#${id}`);
    }

    // Determine which section to show on load
    if (openSection) {
      goToSection(openSection);
    } else if (window.location.hash) {
      goToSection(window.location.hash.substring(1));
    } else {
      goToSection('personal-info');
    }

    // Wire up clicks
    navItems.forEach(item => {
      item.addEventListener('click', function(e) {
   
        const targetId = this.getAttribute('href').slice(1);
        goToSection(targetId);
      });
    });
  });
</script>
	<script>
  document.addEventListener("DOMContentLoaded", function () {
    const navItems = document.querySelectorAll(".sidebar-nav .nav-item");

    navItems.forEach(item => {
      item.addEventListener("click", function () {
        // Remove 'active' from all nav items
        navItems.forEach(i => i.classList.remove("active"));

        // Add 'active' to the clicked item
        this.classList.add("active");
      });
    });
  });
</script>

	<jsp:include page="footer.jsp" />
</body>
</html>