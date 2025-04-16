<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
if(session.getAttribute("username")==null)
    response.sendRedirect(request.getContextPath() + "/login");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NESTAWAY</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css" />
</head>
<body>

    <!-- Navigation Bar -->
		     <div class="nav-container">
		    <div class="nav-logo">
		        <h1>NESTAWAY</h1>
		    </div>
		    
		    <div class="nav-center">
		        <a href="#" class="nav-link">Home</a>
		        <a href="#" class="nav-link">
		            <i data-lucide="heart"></i>
		            Wishlist
		        </a>
		        <a href="#" class="nav-link">About Us</a>
		    </div>
		    
		    <div class="nav-right">
		        <a href="#" class="nav-link">
		            <i data-lucide="user"></i>
		            <%= session.getAttribute("username") %>
		        </a>
		    </div>
		</div>

    <!-- Hero Banner with Search -->
     <div class="hero-banner">
        <div class="search-container">
            <div class="search-box">
                <div class="search-input">
                    <i data-lucide="map-pin"></i>
                    <input type="text" placeholder="Select location" name="location">
                </div>
                <div class="search-input">
                    <i data-lucide="calendar"></i>
                    <input type="text" placeholder="Check-in - Check-out" name="dates">
                </div>
                <div class="search-input">
                    <i data-lucide="users"></i>
                    <input type="text" placeholder="Guests" name="guests">
                </div>
                <button class="search-button">
                    <i data-lucide="search"></i>
                </button>
            </div>
        </div>
    </div>

    <!-- Category Bar -->
    <div class="category-bar">
        <button class="category-button">
            <i data-lucide="flame"></i>
            <span>Trending</span>
        </button>
        <button class="category-button">
            <i data-lucide="bed"></i>
            <span>Rooms</span>
        </button>
        <button class="category-button">
            <i data-lucide="building"></i>
            <span>Hotels</span>
        </button>
        <button class="category-button">
            <i data-lucide="building-2"></i>
            <span>Apartment</span>
        </button>
    </div>

    <!-- Hotel Cards -->
    <div class="hotel-grid">
        <%-- Original backend code (commented out)
        <% 
            // Example of how to iterate through hotels from servlet
            //if(request.getAttribute("hotels") != null) {
               // List<Hotel> hotels = (List<Hotel>)request.getAttribute("hotels");
               // for(Hotel hotel : hotels) {
        %>
        <div class="hotel-card">
            <div class="hotel-image">
                <img src="<%= hotel.getImageUrl() %>" alt="<%= hotel.getName() %>">
                <button class="wishlist-button">
                    <i data-lucide="heart"></i>
                </button>
            </div>
            <div class="hotel-info">
                <h3><%= hotel.getName() %></h3>
                <p><%= hotel.getLocation() %></p>
                <div class="hotel-footer">
                    <div class="price">
                        <span class="amount">$<%= hotel.getPrice() %></span>
                        <span class="per-night">/night</span>
                    </div>
                    <button class="book-button">Book Now</button>
                </div>
            </div>
        </div>
        <% 
               // }
            // }
        %>
        --%>

        <!-- Dummy Hotel Card 1 -->
        <div class="hotel-card">
            <div class="hotel-image">
                <img src="${pageContext.request.contextPath}/resources/images/hotel/hotel3.jpg" alt="Luxury Suite">
                <button class="wishlist-button">
                    <i data-lucide="heart"></i>
                </button>
            </div>
            <div class="hotel-info">
                <h3>Premium City View Suite</h3>
                <p>Downtown Manhattan, NY</p>
                <div class="hotel-footer">
                    <div class="price">
                        <span class="amount">$299</span>
                        <span class="per-night">/night</span>
                    </div>
                    <button class="book-button">Book Now</button>
                </div>
            </div>
        </div>

        <!-- Dummy Hotel Card 2 -->
        <div class="hotel-card">
            <div class="hotel-image">
                <img src="${pageContext.request.contextPath}/resources/images/hotel/hotel1.jpg" alt="Beach Resort">
                <button class="wishlist-button">
                    <i data-lucide="heart"></i>
                </button>
            </div>
            <div class="hotel-info">
                <h3>Oceanfront Paradise Villa</h3>
                <p>Miami Beach, FL</p>
                <div class="hotel-footer">
                    <div class="price">
                        <span class="amount">$459</span>
                        <span class="per-night">/night</span>
                    </div>
                    <button class="book-button">Book Now</button>
                </div>
            </div>
        </div>

        <!-- Dummy Hotel Card 3 -->
        <div class="hotel-card">
            <div class="hotel-image">
                <img src="${pageContext.request.contextPath}/resources/images/hotel/hotel2.jpg" alt="Mountain Lodge">
                <button class="wishlist-button">
                    <i data-lucide="heart"></i>
                </button>
            </div>
            <div class="hotel-info">
                <h3>Alpine Mountain Retreat</h3>
                <p>Swiss Alps, Switzerland</p>
                <div class="hotel-footer">
                    <div class="price">
                        <span class="amount">$599</span>
                        <span class="per-night">/night</span>
                    </div>
                    <button class="book-button">Book Now</button>
                </div>
            </div>
        </div>
                <div class="hotel-card">
            <div class="hotel-image">
                <img src="${pageContext.request.contextPath}/resources/images/hotel/hotel2.jpg" alt="Mountain Lodge">
                <button class="wishlist-button">
                    <i data-lucide="heart"></i>
                </button>
            </div>
            <div class="hotel-info">
                <h3>Alpine Mountain Retreat</h3>
                <p>Swiss Alps, Switzerland</p>
                <div class="hotel-footer">
                    <div class="price">
                        <span class="amount">$599</span>
                        <span class="per-night">/night</span>
                    </div>
                    <button class="book-button">Book Now</button>
                </div>
            </div>
        </div>
    </div>
     

    <script src="https://unpkg.com/lucide@latest"></script>
    <script>
        lucide.createIcons();
    </script>
</body>
</html>