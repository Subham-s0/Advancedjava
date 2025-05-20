<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import="com.Advancedjava.util.Sessionutil" %>
<!DOCTYPE html>
<html>
<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Dancing+Script:wght@400..700&display=swap" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="nav-container">
		 	
    <ul class="nav-list">
    <li><div class="nav-logo"><img src="${pageContext.request.contextPath}/resources/images/Logo.png" /><h1>NestAway</h1>
    </div>
    
        <li><a href="home" class="nav-link">HOME</a></li>
        <li><a href="${pageContext.request.contextPath}/Profile" class="nav-link">PROFILE</a></li>
        
        <li><a href="#" class="nav-link">ABOUT US</a></li>
        
         <li>
                    <div class="profile-section nav-link"> 
                    <%= Sessionutil.getAttribute(request,"username") %>
                    <div class="profile-picture"><img src="${pageContext.request.contextPath}/Profile_pictureservlet" alt="Profile"></div>
                        <div class="profile-dropdown">
                            <ul>
                                <li><a href="Property" class="drop-down-list">Properties</a>
                                <li><a href="#" class="drop-down-list">My Bookings</a>
                                <li><a href="#" class="drop-down-list"> Favourites</a>
                                  <li><a href="${pageContext.request.contextPath}/logout" class="drop-down-list">Logout<i data-lucide="log-out"></i></a>
                               
                            </ul>
                        </div>
                    
                </div>	           
        </li>
    </ul>
		</div>
<script src="https://unpkg.com/lucide@latest"></script>
<script>
    // Initialize Lucide icons
    lucide.createIcons();

    // Get DOM elements
    const profileSection = document.querySelector('.profile-section');
    const profileDropdown = document.querySelector('.profile-dropdown');

    // Toggle dropdown on click
    profileSection.addEventListener('click', (e) => {
        e.stopPropagation();
        profileDropdown.classList.toggle('show');
    });

    // Close dropdown when clicking outside
    document.addEventListener('click', () => {
        profileDropdown.classList.remove('show');
    });

    // Prevent dropdown from closing when clicking inside it
    profileDropdown.addEventListener('click', (e) => {
        e.stopPropagation();
    });
</script>
</body>
</html>