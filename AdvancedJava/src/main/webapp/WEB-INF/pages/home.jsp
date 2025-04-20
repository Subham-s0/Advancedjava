<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.Advancedjava.util.Sessionutil" %>
    
<%
if(Sessionutil.getAttribute(request,"username")==null)
    response.sendRedirect(request.getContextPath() + "/login");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NESTAWAY</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/searchbar.css" />
 <script src="https://unpkg.com/lucide@latest"></script>

</head>
<body>

    <!-- Navigation Bar -->
    <jsp:include page="header.jsp" />
 <!-- Hero Banner with Search -->
	 <div class="search-container">
	            <jsp:include page="searchbar.jsp" />
	        </div>
	     <div class="hero-banner">
			 
	    </div>
	    <div class="category-bar">
	        <!-- Categories will be dynamically inserted here -->
	    </div>
	    <div class="hotel-grid">
	        <!-- Hotels will be dynamically inserted here -->
	    </div>
    <script src="https://unpkg.com/lucide@latest"></script>
    <script src="${pageContext.request.contextPath}/javascript/categorysection.js"></script>
    <script>
        lucide.createIcons();
    </script>
    <script>
  const searchContainer = document.querySelector('.search-container');

  window.addEventListener('scroll', () => {
	  const scrollY = window.scrollY;
	  const searchContainer = document.querySelector('.search-container');

	  if (scrollY > 150) {
	    searchContainer.classList.add('sticky');
	  } else {
	    searchContainer.classList.remove('sticky');
	  }
	});
  
  // Initialize date picker
  flatpickr("#dateRange", {
      mode: "range",
      minDate: "today",
      dateFormat: "Y-m-d",
      placeholder: "Check-in - Check-out"
  });

  // Location dropdown functionality
  const locationInput = document.getElementById('location');
  const locationDropdown = document.querySelector('.location-dropdown');
  const locationItems = document.querySelectorAll('.location-item');

  locationInput.addEventListener('click', () => {
      locationDropdown.style.display = locationDropdown.style.display === 'block' ? 'none' : 'block';
  });

  document.addEventListener('click', (e) => {
      if (!locationInput.contains(e.target) && !locationDropdown.contains(e.target)) {
          locationDropdown.style.display = 'none';
      }
  });

  locationItems.forEach(item => {
      item.addEventListener('click', () => {
          locationInput.value = item.textContent;
          locationDropdown.style.display = 'none';
      });
  });

  // Guest dropdown functionality
  const guestInput = document.getElementById('guestInput');
  const guestDropdown = document.querySelector('.guest-dropdown');

  guestInput.addEventListener('click', (e) => {
      e.stopPropagation();
      guestDropdown.style.display = guestDropdown.style.display === 'block' ? 'none' : 'block';
  });

  document.addEventListener('click', (e) => {
      if (!guestDropdown.contains(e.target) && !guestInput.contains(e.target)) {
          guestDropdown.style.display = 'none';
      }
  });
  
</script>

</body>
</html>