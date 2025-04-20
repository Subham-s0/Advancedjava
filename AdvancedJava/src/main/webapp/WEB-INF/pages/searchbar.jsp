<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Bar</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/searchbar.css" />
    
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
</head>
<body>
    <form class="search-form" action="search" method="GET">
        
            <div class="search-box">
                <!-- Location Search with Dropdown -->
                <div class="search-input location-input">
                    <i data-lucide="map-pin"></i>
                    <input type="text" id="location" name="location" placeholder="Select location">
                    <div class="location-dropdown">
                        <div class="location-item">New York, USA</div>
                        <div class="location-item">London, UK</div>
                        <div class="location-item">Paris, France</div>
                        <div class="location-item">Tokyo, Japan</div>
                        <div class="location-item">Dubai, UAE</div>
                    </div>
                </div>

                <!-- Date Input -->
                <div class="search-input">
                    <i data-lucide="calendar"></i>
                    <input type="text" id="dateRange" name="dates" placeholder="Check-in - Check-out">
                </div>

                <!-- Guest Selector -->
                <div class="search-input guest-selector">
                    <i data-lucide="users"></i>
                    <input type="text" id="guestInput" name="guests" placeholder="Add guests" readonly>
                    <div class="guest-dropdown">
                        <div class="guest-option">
                            <div class="guest-type">
                                <h4>Adults</h4>
                                <p>Ages 13 or above</p>
                            </div>
                            <div class="stepper">
                                <button type="button" class="stepper-btn">-</button>
                                <span class="stepper-number">0</span>
                                <button type="button" class="stepper-btn">+</button>
                            </div>
                        </div>
                        <div class="guest-option">
                            <div class="guest-type">
                                <h4>Children</h4>
                                <p>Ages 2-12</p>
                            </div>
                            <div class="stepper">
                                <button type="button" class="stepper-btn">-</button>
                                <span class="stepper-number">0</span>
                                <button type="button" class="stepper-btn">+</button>
                            </div>
                        </div>
                    </div>
                </div>

                <button type="submit" class="search-button">
                    <i data-lucide="search"></i>
                </button>
            </div>
    </form>

    <script src="https://unpkg.com/lucide@latest"></script>
    <script>
        lucide.createIcons();

        // Initialize date picker

    </script>
</body>
</html> 