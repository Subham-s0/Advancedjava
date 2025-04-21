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
<header>
<jsp:include page="header.jsp" />
</header>
    <!-- Navigation Bar -->
    
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
    <script>window.contextPath = "${pageContext.request.contextPath}";</script>
    <script src="${pageContext.request.contextPath}/javascript/categorysection.js"></script>
    <script>
        lucide.createIcons();
    </script>
    <script>
  const searchContainer = document.querySelector('.search-container');

  window.addEventListener('scroll', () => {
      const scrollY = window.scrollY;
      
      if (scrollY > 150) {
          searchContainer.classList.add('sticky');
          // Add compact class when scrolling down (unless already expanded)
          if (!searchContainer.classList.contains('expanded')) {
              searchContainer.classList.add('compact');
          }
      } else {
          searchContainer.classList.remove('sticky');
          searchContainer.classList.remove('compact');
          searchContainer.classList.remove('expanded');
      }
  });

  // Handle clicks on search container or icons
  searchContainer.addEventListener('click', (e) => {
      // Check if click is on any search input or icon within the container
      if (e.target.closest('.search-input') || e.target.closest('[data-lucide]')) {
          if (searchContainer.classList.contains('sticky') && 
              searchContainer.classList.contains('compact')) {
              // Remove compact class and add expanded marker
              searchContainer.classList.remove('compact');
              searchContainer.classList.add('expanded');
          }
      }
  });

  // Close expanded search when clicking outside
  document.addEventListener('click', (e) => {
      if (!searchContainer.contains(e.target) && 
          searchContainer.classList.contains('sticky') &&
          searchContainer.classList.contains('expanded')) {
          // Return to compact mode
          searchContainer.classList.add('compact');
          searchContainer.classList.remove('expanded');
      }
  });

  // Initialize date picker - will work as it searches within entire document
  flatpickr("#dateRange", {
      mode: "range",
      minDate: "today",
      dateFormat: "Y-m-d",
      placeholder: "Check-in - Check-out"
  });

  // Location dropdown functionality - updated to work with included content
  document.querySelector('.search-container').addEventListener('click', function(e) {
      // Handle location dropdown toggle
      if (e.target.closest('.location-input')) {
          const dropdown = this.querySelector('.location-dropdown');
          dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
          this.querySelector('.guest-dropdown').style.display = 'none';
      }
      
      // Handle guest dropdown toggle
      if (e.target.closest('.guest-selector')) {
          const dropdown = this.querySelector('.guest-dropdown');
          dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
          this.querySelector('.location-dropdown').style.display = 'none';
      }
      
      // Handle location item selection
      if (e.target.closest('.location-item')) {
          const item = e.target.closest('.location-item');
          this.querySelector('#location').value = item.textContent;
          this.querySelector('.location-dropdown').style.display = 'none';
      }
  });

  // Close dropdowns when clicking outside
  document.addEventListener('click', function(e) {
      const searchContainer = document.querySelector('.search-container');
      if (!e.target.closest('.search-input')) {
          searchContainer.querySelector('.location-dropdown').style.display = 'none';
          searchContainer.querySelector('.guest-dropdown').style.display = 'none';
      }
  });
  
</script>
<jsp:include page="footer.jsp" />
</body>
</html>