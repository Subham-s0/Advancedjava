<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>NestAway</title>
    <!-- Lucide Icons -->
    <script src="https://unpkg.com/lucide@latest"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin/Adminheader.css" />
</head>
<body>
<div class="topbar">
         
            <form action="#" method="get" class="search-bar-container">
           
            <input type="text" 
                   name="query" 
                   placeholder="Search for something" 
                   class="search-bar"
                   required>
                    <button type="submit" class="search-button">
                <i data-lucide="search"></i>
            </button>
        </form>
     
        <div class="user-profile">
          <div class="profile-picture"><img src="${pageContext.request.contextPath}/Profile_pictureservlet" alt="Profile"></div>
      
    </div>
</div>

<!-- Sidebar -->
<div class="sidebar">
<div class="sidebar-content">
    <div class="sidebar-logo"><img src="${pageContext.request.contextPath}/resources/images/logoReverse.png" /><h1>NestAway</h1>
    </div>
    <div class="sidebar-items  ${activeSection == 'dashboard' ? 'active' : ''}" > 
        <a href="${pageContext.request.contextPath}/admindashboard">
            <i data-lucide="home"></i>
            <span>Dashboard</span>
        </a>
    </div>
    <div class="sidebar-items  ${activeSection == 'property' ? 'active' : ''}">
        <a href="${pageContext.request.contextPath}/propertydashboard">
            <i data-lucide="bed"></i>
            <span>Property</span>
        </a>
    </div>
    <div class="sidebar-items  ${activeSection == 'bookings' ? 'active' : ''}">
        <a href="${pageContext.request.contextPath}/bookingsdashboard">
            <i data-lucide="calendar-check"></i>
            <span>Bookings</span>
        </a>
         </div>
          <div class="sidebar-items  ${activeSection == 'user' ? 'active' : ''}">
        <a href="${pageContext.request.contextPath}/usersdashboard">
            <i data-lucide="user"></i>
            <span>Users</span>
        </a>
         </div>
         <div class="sidebar-items  ${activeSection == 'users' ? 'active' : ''}">
        <a href="${pageContext.request.contextPath}/reportsdashboard">
            <i data-lucide="newspaper"></i>
            <span>Reports</span>
        </a>
         </div>
        </div>
        <div class="logout">
        <div class="sidebar-items">
            <a href="${pageContext.request.contextPath}/logout">
                <i data-lucide="log-out"></i>
                <span>Logout</span>
            </a>
       
    </div>
</div>
    </div>
   
    
<script>
    window.addEventListener('DOMContentLoaded', () => {
        lucide.createIcons();
    });
</script>

</body>
</html>