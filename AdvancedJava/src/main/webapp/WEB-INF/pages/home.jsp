<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.Advancedjava.util.Sessionutil" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NESTAWAY</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/searchbar.css" />
 <script src="https://unpkg.com/lucide@latest"></script>
<style>
    /* Hotel Image Gallery Styles */
    .hotel-image-gallery {
        position: relative;
        height: 250px;
        overflow: hidden;
    }
    
    .main-image-container {
        position: relative;
        height: 100%;
    }
    
    .main-image-container img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        transition: transform 0.3s ease;
    }
    
    .thumbnail-scrollbar {
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        height: 50px;
        background: rgba(0,0,0,0.5);
        overflow-x: auto;
        overflow-y: hidden;
        display: none; /* Hidden by default */
        transition: all 0.3s ease;
    }
    
    .thumbnails-container {
        display: flex;
        height: 100%;
        padding: 5px;
        gap: 5px;
    }
    
    .thumbnail {
        height: 100%;
        width: auto;
        cursor: pointer;
        opacity: 0.7;
        transition: opacity 0.2s ease;
    }
    
    .thumbnail:hover {
        opacity: 1;
    }
    
    /* Show thumbnails on hover */
    .hotel-image-gallery:hover .thumbnail-scrollbar {
        display: block;
    }
    
    .thumbnail-scrollbar::-webkit-scrollbar {
        height: 5px;
    }
    
    .thumbnail-scrollbar::-webkit-scrollbar-thumb {
        background: rgba(255,255,255,0.5);
    }
</style>
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
	    
	    <% 
String error = (String) Sessionutil.getAttribute(request, "error");
if (error != null && !error.isEmpty()) { 
%>
    <div class="error-message" id="errorMessage">
        <%= error %>
        <i data-lucide="x" class="close-icon" onclick="closeErrorMessage()"></i>
    </div>
    <script>
        // Auto-hide error message after 5 seconds
        setTimeout(function() {
            document.getElementById('errorMessage').style.display = 'none';
            // Clear the error from session
            fetch('<%= request.getContextPath() %>/clearError', {
                method: 'POST',
                credentials: 'include'
            });
        }, 5000);
        
        function closeErrorMessage() {
            document.getElementById('errorMessage').style.display = 'none';
            // Clear the error from session
            fetch('<%= request.getContextPath() %>/clearError', {
                method: 'POST',
                credentials: 'include'
            });
        }
    </script>
<%
    // Clear the error message from session immediately after displaying
    Sessionutil.removeAttribute(request, "error");
}
%>
	   <!-- Category Filter Bar -->
    <div class="category-bar">
        <!-- Add "All" category first -->
        <button class="category-button active" data-category-id="0">
            <i data-lucide="grid"></i>
            <span>All</span>
        </button>
        
        <c:choose>
            <c:when test="${not empty categories}">
                <c:forEach items="${categories}" var="category">
                    <button class="category-button" 
                            data-category-id="${category.categoryId}">
                        <i data-lucide="${category.categoryIcon}"></i>
                        <span>${category.categoryName}</span>
                    </button>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>No categories available</p>
            </c:otherwise>
        </c:choose>
    </div>
    
    <!-- Property Grid -->
    <div class="hotel-grid">
   <jsp:include page="/WEB-INF/pages/Property-grid.jsp" />
    </div>
    <script src="https://unpkg.com/lucide@latest"></script>
    <script>window.contextPath = "${pageContext.request.contextPath}";</script>
    
    <script>
        lucide.createIcons();
    </script>
    <script>
 // Add this script to your home.jsp file, replacing the existing category filtering code

    document.addEventListener('DOMContentLoaded', function() {
        console.log("DOM fully loaded - initializing category filtering");
        
        // Initialize Lucide icons
        lucide.createIcons();
        
        // Set up event listeners for category buttons
        const categoryButtons = document.querySelectorAll('.category-button');
        const hotelGrid = document.querySelector('.hotel-grid');
        
        console.log("Found " + categoryButtons.length + " category buttons");
        
        // Set the "All" button (index 0) as active initially
        if(categoryButtons.length > 0) {
            categoryButtons[0].classList.add('active');
        }
        
        // Add click event listeners to all category buttons
        categoryButtons.forEach(button => {
            const categoryId = button.getAttribute('data-category-id');
            console.log("Button with category ID: " + categoryId);
            
            button.addEventListener('click', function() {
                console.log("Category button clicked: " + categoryId);
                
                // Update active button styling
                categoryButtons.forEach(btn => btn.classList.remove('active'));
                this.classList.add('active');
                
                // Load properties via AJAX
                loadProperties(categoryId);
            });
        });
        
        // Function to load properties by category using AJAX
        function loadProperties(categoryId) {
        hotelGrid.innerHTML = '<div class="loading">Loading properties...</div>';     
            fetch(window.contextPath + "/filter-hotels?categoryId=" + categoryId)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok: ' + response.status);
                    }
                    return response.text();
                })
                .then(html => {
                    hotelGrid.innerHTML = html;
                    lucide.createIcons();
                    console.log("Properties loaded successfully");
                })
                .catch(error => {
                    console.error('Error loading properties:', error);
                    hotelGrid.innerHTML = '<div class="error">Error loading properties. Please try again.</div>';
                });
        }
        
        initializeSearchContainer();
    });

    function initializeSearchContainer() {
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
            
            // Handle location dropdown toggle
            if (e.target.closest('.location-input')) {
                const dropdown = searchContainer.querySelector('.location-dropdown');
                dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
                searchContainer.querySelector('.guest-dropdown').style.display = 'none';
            }
            
            // Handle guest dropdown toggle
            if (e.target.closest('.guest-selector')) {
                const dropdown = searchContainer.querySelector('.guest-dropdown');
                dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
                searchContainer.querySelector('.location-dropdown').style.display = 'none';
            }
            
            // Handle location item selection
            if (e.target.closest('.location-item')) {
                const item = e.target.closest('.location-item');
                searchContainer.querySelector('#location').value = item.textContent;
                searchContainer.querySelector('.location-dropdown').style.display = 'none';
            }
        });

        // Close expanded search when clicking outside
        document.addEventListener('click', (e) => {
            if (!searchContainer.contains(e.target)) {
                // Close dropdowns
                searchContainer.querySelector('.location-dropdown').style.display = 'none';
                searchContainer.querySelector('.guest-dropdown').style.display = 'none';
                
                // Return to compact mode if expanded
                if (searchContainer.classList.contains('sticky') &&
                    searchContainer.classList.contains('expanded')) {
                    searchContainer.classList.add('compact');
                    searchContainer.classList.remove('expanded');
                }
            }
        });

        // Initialize date picker if it exists
        if (typeof flatpickr !== 'undefined' && document.getElementById('dateRange')) {
            flatpickr("#dateRange", {
                mode: "range",
                minDate: "today",
                dateFormat: "Y-m-d",
                placeholder: "Check-in - Check-out"
            });
        }
    }
</script>
<jsp:include page="footer.jsp" />
</body>
</html>