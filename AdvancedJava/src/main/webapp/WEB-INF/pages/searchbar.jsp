<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search Properties</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/searchbar.css" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
</head>
<body>
<form class="search-form" action="Result" method="GET">

<div class="search-box">
    <!-- Location Search with Dynamic Dropdown -->
    <div class="search-input location-input">
        <i data-lucide="map-pin"></i>
        <input type="text" id="location" name="location" placeholder="Select location" autocomplete="off">
        <div class="location-dropdown" id="locationDropdown">
            <!-- Locations will be populated dynamically -->
        </div>
    </div>

    <!-- Date Input -->
    <div class="search-input">
        <i data-lucide="calendar"></i>
        <input type="text" id="dateRange" name="dates" placeholder="Check-in - Check-out" readonly>
    </div>

    <!-- Guest Selector -->
    <div class="search-input guest-selector">
        <i data-lucide="users"></i>
        <input type="text" id="guestInput" name="guests" placeholder="Add guests" readonly>
        <input type="hidden" id="totalGuests" name="totalGuestsCount" value="1">
        <div class="guest-dropdown" id="guestDropdown">
            <div class="guest-option">
                <div class="guest-type">
                    <h4>Adults</h4>
                    <p>Ages 13 or above</p>
                </div>
                <div class="stepper">
                    <button type="button" class="stepper-btn" onclick="updateGuests('adults', -1)">-</button>
                    <span class="stepper-number" id="adultsCount">1</span>
                    <button type="button" class="stepper-btn" onclick="updateGuests('adults', 1)">+</button>
                </div>
            </div>
            <div class="guest-option">
                <div class="guest-type">
                    <h4>Children</h4>
                    <p>Ages 2-12</p>
                </div>
                <div class="stepper">
                    <button type="button" class="stepper-btn" onclick="updateGuests('children', -1)">-</button>
                    <span class="stepper-number" id="childrenCount">0</span>
                    <button type="button" class="stepper-btn" onclick="updateGuests('children', 1)">+</button>
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

// Guest counter variables
let adults = 1;
let children = 0;
let maxGuests = 20; // Will be updated from server

// Load locations from database
async function loadLocations() {
    try {
        const response = await fetch('${pageContext.request.contextPath}/api/locations');
        const locations = await response.json();
        
        const dropdown = document.getElementById('locationDropdown');
        dropdown.innerHTML = '';
        
        locations.forEach(location => {
            const item = document.createElement('div');
            item.className = 'location-item';
            item.textContent = location;
            item.onclick = () => selectLocation(location);
            dropdown.appendChild(item);
        });
    } catch (error) {
        console.error('Failed to load locations:', error);
    }
}

// Select location
function selectLocation(location) {
    document.getElementById('location').value = location;
    document.getElementById('locationDropdown').style.display = 'none';
}

// Location input handling
document.getElementById('location').addEventListener('focus', function() {
    document.getElementById('locationDropdown').style.display = 'block';
    loadLocations();
});

document.getElementById('location').addEventListener('input', function() {
    const searchTerm = this.value.toLowerCase();
    const items = document.querySelectorAll('.location-item');
    
    items.forEach(item => {
        if (item.textContent.toLowerCase().includes(searchTerm)) {
            item.style.display = 'block';
        } else {
            item.style.display = 'none';
        }
    });
});

// Hide dropdown when clicking outside
document.addEventListener('click', function(e) {
    if (!e.target.closest('.location-input')) {
        document.getElementById('locationDropdown').style.display = 'none';
    }
    if (!e.target.closest('.guest-selector')) {
        document.getElementById('guestDropdown').style.display = 'none';
    }
});

// Initialize date picker
flatpickr("#dateRange", {
    mode: "range",
    dateFormat: "Y-m-d",
    minDate: "today",
    onChange: function(selectedDates, dateStr, instance) {
        if (selectedDates.length === 2) {
            const checkIn = flatpickr.formatDate(selectedDates[0], "Y-m-d");
            const checkOut = flatpickr.formatDate(selectedDates[1], "Y-m-d");
            document.getElementById('dateRange').value = checkIn + " to " + checkOut;
        }
    }
});

// Guest selector handling
document.getElementById('guestInput').addEventListener('click', function() {
    const dropdown = document.getElementById('guestDropdown');
    dropdown.style.display = dropdown.style.display === 'none' ? 'block' : 'none';
});

// Update guest counts
function updateGuests(type, change) {
    if (type === 'adults') {
        adults = Math.max(1, Math.min(maxGuests, adults + change));
        document.getElementById('adultsCount').textContent = adults;
    } else if (type === 'children') {
        children = Math.max(0, Math.min(maxGuests - adults, children + change));
        document.getElementById('childrenCount').textContent = children;
    }
    
    updateGuestDisplay();
}

// Update guest display text
function updateGuestDisplay() {
    const total = adults + children;
    let text = '';
    
    if (adults > 0) {
        text += adults + ' adult' + (adults > 1 ? 's' : '');
    }
    
    if (children > 0) {
        if (text) text += ', ';
        text += children + ' child' + (children > 1 ? 'ren' : '');
    }
    
    document.getElementById('guestInput').value = text;
    document.getElementById('totalGuests').value = total;
}

// Load max guest capacity
async function loadMaxGuests() {
    try {
        const response = await fetch('${pageContext.request.contextPath}/api/maxGuests');
        const data = await response.json();
        maxGuests = data.maxGuests || 20;
    } catch (error) {
        console.error('Failed to load max guests:', error);
    }
}

// Form validation
document.querySelector('.search-form').addEventListener('submit', function(e) {
    const location = document.getElementById('location').value;
    const dates = document.getElementById('dateRange').value;
    
    if (!location.trim()) {
        alert('Please select a location');
        e.preventDefault();
        return;
    }
    
    if (!dates.trim()) {
        alert('Please select check-in and check-out dates');
        e.preventDefault();
        return;
    }
    
    // Validate date format
    if (!dates.includes(' to ')) {
        alert('Please select both check-in and check-out dates');
        e.preventDefault();
        return;
    }
});

// Initialize on page load
document.addEventListener('DOMContentLoaded', function() {
    updateGuestDisplay();
    loadMaxGuests();
});
</script>
</body>
</html>