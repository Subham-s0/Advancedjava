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
         
           <form action="${pageContext.request.contextPath}/Searchrouter" method="get" class="search-bar-container">
    <select name="query" class="search-bar" required>
        <option value="" disabled selected>Select a page...</option>
        <option value="dashboard">Dashboard</option>
        <option value="property dashboard">Property Dashboard</option>
        <option value="add property">Add Property</option>
        <option value="add images">Add Images</option>
        <option value="add amenities">Add Amenities</option>
        <option value="update property">Update Property</option>
        <option value="users dashboard">Users Dashboard</option>
        <option value="bookings dashboard">Bookings Dashboard</option>
        <option value="admin profile">Admin Profile</option>
    </select>
    <button type="submit" class="search-button">
        <i data-lucide="search"></i>
    </button>
</form>

<div class="user-profile">
    <div class="profile-picture">
        <a href="${pageContext.request.contextPath}/adminprofile">
            <img src="${pageContext.request.contextPath}/Profile_pictureservlet" alt="Profile">
        </a>
    </div>
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