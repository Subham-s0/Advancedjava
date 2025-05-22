<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.Advancedjava.util.Sessionutil"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nestaway</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/propertydescription.css" />
</head>
<script src="https://unpkg.com/lucide@latest"></script>
<body>
	<header>
		<jsp:include page="header.jsp" />
	</header>
<body>
	<div class="top">
		<div class="gallery">
			<div class="gallery-flex">
				<div class="big-image" id="main-image">
					<c:choose>
						<c:when test="${not empty selectedProperty.images}">
							<img
								src="${pageContext.request.contextPath}/resources/images/property/${selectedProperty.images[0].fileName}"
								alt="${selectedProperty.propertyName}"
								onerror="this.src='${pageContext.request.contextPath}/resources/images/property/default-img.jpg'">
						</c:when>
						<c:otherwise>
							<img
								src="${pageContext.request.contextPath}/resources/images/property/default-img.jpg"
								alt="Default property image">
						</c:otherwise>
					</c:choose>
				</div>
				<div class="small-images-vertical">
					<c:forEach items="${selectedProperty.images}" var="image"
						varStatus="loop">
						<img class="thumb"
							src="${pageContext.request.contextPath}/resources/images/property/${image.fileName}"
							alt="Thumbnail ${loop.index + 1}"
							onerror="this.src='${pageContext.request.contextPath}/resources/images/property/default-img.jpg'"
							onclick="document.getElementById('main-image').querySelector('img').src = this.src">
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="price-box">
			<%
			String error = (String) session.getAttribute("error");
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
			String success = (String) session.getAttribute("success");
			%>
			<div
				class="success-message <%=(success != null && !success.isEmpty()) ? "visible" : ""%>">
				<%
				if (success != null && !success.isEmpty()) {
				%>
				<p><%=success%></p>
				<%
				}
				Sessionutil.removeAttribute(request, "success");
				Sessionutil.removeAttribute(request, "error");
				%>
			</div>

			<!-- Booking Form -->
			<form id="bookingForm"
				action="${pageContext.request.contextPath}/BookingController"
				method="post" autocomplete="off">
				<input type="hidden" name="propertyId"
					value="${selectedProperty.propertyId}" />
					
				<h2>$ ${selectedProperty.pricePerNight} / Night</h2>

				<!-- Check-in/Check-out -->
				<div class="dates">
					<div>
						<label for="checkin">Check-in</label><br> <input type="date"
							id="checkin" name="checkin" required>
					</div>
					<div>
						<label for="checkout">Check-out</label><br> <input
							type="date" id="checkout" name="checkout" required>
					</div>
				</div>

				<!-- Guest Selection -->
				<div class="guests">
					<div class="guest-container">
						<div class="search-input guest-selector">
							<i data-lucide="users"></i> <input type="text" id="guestInput"
								placeholder="Add guests" readonly> <input type="hidden"
								id="totalGuests" name="totalGuests">
						</div>
						<div class="guest-dropdown">
							<!-- Adult -->
							<div class="guest-option" data-type="adults">
								<div class="guest-type">
									<h4>Adults</h4>
									<p>Ages 13 or above</p>
								</div>
								<div class="stepper">
									<button type="button" class="stepper-btn decrease disabled">-</button>
									<span class="stepper-number">1</span>
									<button type="button" class="stepper-btn increase">+</button>
								</div>
							</div>

							<!-- Children -->
							<div class="guest-option" data-type="children">
								<div class="guest-type">
									<h4>Children</h4>
									<p>Ages 2-12</p>
								</div>
								<div class="stepper">
									<button type="button" class="stepper-btn decrease disabled">-</button>
									<span class="stepper-number">0</span>
									<button type="button" class="stepper-btn increase">+</button>
								</div>
							</div>

							<!-- Infants -->
							<div class="guest-option" data-type="infants">
								<div class="guest-type">
									<h4>Infants</h4>
									<p>Under 2</p>
								</div>
								<div class="stepper">
									<button type="button" class="stepper-btn decrease disabled">-</button>
									<span class="stepper-number">0</span>
									<button type="button" class="stepper-btn increase">+</button>
								</div>
							</div>
						</div>
					</div>
				</div>

				<!-- Charges Section -->
				<div class="charges">
					<div class="charge-line">
						<span>Charge per night</span> <span id="charge-per-night">$${selectedProperty.pricePerNight}</span>
					</div>
					<div class="charge-line">
						<span>Cleaning charge</span> <span id="cleaning-charge">$${selectedProperty.cleaningFee}</span>
					</div>
					<div class="charge-line">
						<span>Service charge</span> <span id="service-charge">$${selectedProperty.serviceFee}</span>
					</div>
					<div class="charge-line">
						<span>Subtotal (Before Tax)</span> <span id="subtotal">$0</span>
					</div>
					<div class="charge-line">
						<span>Tax (${selectedProperty.taxRate}%)</span> <span id="tax">$0</span>
					</div>
					<div class="charge-line total">
						<span><strong>Total charges</strong></span> <span
							id="total-charges"><strong>$0</strong></span>
					</div>
				</div>

				<!-- Booking Button -->
				<div class="buttons">
					<button type="submit" class="book-now">BOOK NOW</button>
				</div>

				<!-- Pass propertyId to backend -->
				<input type="hidden" name="propertyId"
					value="${selectedProperty.propertyId}">
			</form>
			<form action="${pageContext.request.contextPath}/WishListController"
				method="post">
				<input type="hidden" name="propertyId"
					value="${selectedProperty.propertyId}" />
				<c:choose>
					<c:when test="${wishlistIds.contains(selectedProperty.propertyId)}">
						<button type="submit" class="add-to-cart">✔ Already in
							Wishlist</button>
					</c:when>
					<c:otherwise>
						<button type="submit" class="add-to-cart">♡ Add to
							Wishlist</button>
					</c:otherwise>
				</c:choose>
			</form>
		</div>
	</div>
	<div class="tabs">
		<button class="tab active" id="overviewBtn">Overview</button>
		<button class="tab" id="facilityBtn">Facilities</button>
		<button class="tab" id="reviewBtn">Review</button>
	</div>
	<div class="container">
		<div class="hotel-info tab-content" id="overview">
			<h2>${selectedProperty.propertyName}</h2>
			<p class="location">${selectedProperty.propertyCity},
				${selectedProperty.propertyCountry}</p>
			<h3>Property Description</h3>
			<p class="about">${selectedProperty.propertyDescription}</p>
			<h3>✔ Hosted by: ${selectedProperty.hostName}</h3>

			<div class="facilities">
				<h3>Property Details</h3>
				<div class="Cat-item">
					<span class="category-tag"> <i class="meta-icon"
						data-lucide="${category.categoryIcon}"></i>
						${category.categoryName}
					</span>
				</div>
				<div class="facility-item">
					<i class="meta-icon" data-lucide="bed"></i> <span>Total
						bedrooms: ${selectedProperty.totalBedrooms}</span>
				</div>
				<div class="facility-item">
					<i class="meta-icon" data-lucide="bath"></i> <span>Total
						bathrooms: ${selectedProperty.totalBath}</span>
				</div>
				<div class="facility-item">
					<i class="meta-icon" data-lucide="users"></i> <span>Max
						guests: ${selectedProperty.maximumGuests}</span>
				</div>

				<div class="facility-item">
					<i class="meta-icon" data-lucide="layout"></i> <span>${selectedProperty.totalRooms}
						Rooms</span>
				</div>
			</div>
		</div>

		<!-- Facilities Section -->
		<div id="facility" class="tab-content" style="display: none;">
			<h3>Facility</h3>
			<div class="facility">

				<c:forEach items="${propertyAmenities}" var="amenity">
					<div class="amenity-item">
						<span><i data-lucide="${amenity.amenityIcon}"
							class="amenity-icon"></i></span> <span class="amenity-name">${amenity.amenityName}</span>
						<div class="amenity-remove-btn">✔</div>

					</div>
				</c:forEach>
			</div>
		</div>

		<!-- Reviews Section -->
		<div id="reviews" class="tab-content" style="display: none;">
			
    <h3>Guest Reviews</h3>
    <div class="review-cards">
        <c:forEach var="ru" items="${reviewsWithUsers}">
            <div class="review-card">
                <div class="review-user">
                  <div class ="review-img" style="display:flex; gap:20px; justify-content:flex-start; align-items:center; "> <img src="${pageContext.request.contextPath}/resources/images/profile/${ru.user.userProfilePicture}" 
                  alt="${ru.user.userName}" class="user-img" style="width:50px; height:50px; border-radius:50%;"/>
					
					<span>${ru.user.userName}</span></div>
                </div>
                <div class="review-rating">
                    <c:forEach begin="1" end="5" var="i">
                        <span style="color: ${i <= ru.review.rating ? '#ffc107' : '#e0e0e0'}">&#9733;</span>
                    </c:forEach>
                </div>
                <p>${ru.review.comment}</p>
                <small><fmt:formatDate value="${ru.review.reviewDate}" pattern="MMM dd, yyyy" /></small>
            </div>
        </c:forEach>
    </div>
</div>
	</div>
	<script>
    // Initialize Lucide icons
    lucide.createIcons();
    
    // Thumbnail image gallery
    const thumbnails = document.querySelectorAll('.thumb');
    const mainImage = document.getElementById('main-image').querySelector('img');
    
    thumbnails.forEach(thumb => {
        thumb.addEventListener('click', () => {
            mainImage.src = thumb.src;
        });
    });

    // Tab Switching Logic
    const overviewBtn = document.getElementById('overviewBtn');
    const facilityBtn = document.getElementById('facilityBtn');
    const reviewBtn = document.getElementById('reviewBtn');
    
    function switchTab(activeTab) {
        // Remove active class from all buttons
        document.querySelectorAll('.tab').forEach(btn => {
            btn.classList.remove('active');
        });
        
        // Hide all tab contents
        document.querySelectorAll('.tab-content').forEach(content => {
            content.style.display = 'none';
        });
        
        // Show selected tab and mark button as active
        document.getElementById(activeTab).style.display = 'block';
        document.getElementById(activeTab + 'Btn').classList.add('active');
    }
    
    // Event Listeners for tabs
    overviewBtn.addEventListener('click', () => switchTab('overview'));
    facilityBtn.addEventListener('click', () => switchTab('facility'));
    reviewBtn.addEventListener('click', () => switchTab('reviews'));
    
    // Initialize with overview tab
    // This is actually redundant because we already set it in the HTML, but keeping for clarity
    switchTab('overview');
    
    
</script>

	<script>
    lucide.createIcons();

    const chargePerNight = ${selectedProperty.pricePerNight};
    const cleaningCharge = ${selectedProperty.cleaningFee};
    const serviceCharge = ${selectedProperty.serviceFee};
    const taxRate = ${selectedProperty.taxRate / 100};
    const maxGuests = ${selectedProperty.maximumGuests};

    const guestCounts = { adults: 1, children: 0, infants: 0 };

    function calculateCharges() {
        const checkin = new Date(document.getElementById("checkin").value);
        const checkout = new Date(document.getElementById("checkout").value);

        if (isNaN(checkin) || isNaN(checkout) || checkout <= checkin) {
            document.getElementById("subtotal").textContent = '$0';
            document.getElementById("tax").textContent = '$0';
            document.getElementById("total-charges").textContent = '$0';
            return;
        }

        const nights = Math.round((checkout - checkin) / (1000 * 60 * 60 * 24));
        const subtotal = (chargePerNight * nights) + cleaningCharge + serviceCharge;
        const tax = subtotal * taxRate;
        const total = subtotal + tax;

        document.getElementById("subtotal").textContent = '$' + subtotal.toFixed(2);
        document.getElementById("tax").textContent = '$' + tax.toFixed(2);
        document.getElementById("total-charges").textContent = '$' + total.toFixed(2);
    }

    document.getElementById("checkin").addEventListener("change", calculateCharges);
    document.getElementById("checkout").addEventListener("change", calculateCharges);

    document.addEventListener("DOMContentLoaded", () => {
        const guestInput = document.getElementById("guestInput");
        const totalGuestsField = document.getElementById("totalGuests");
        const dropdown = document.querySelector(".guest-dropdown");
        const selector = document.querySelector(".guest-selector");

        selector.addEventListener("click", () => {
            dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";
        });

        document.addEventListener("click", (e) => {
            // Don't close dropdown if clicking within the guest dropdown itself
            if (!selector.contains(e.target) && !dropdown.contains(e.target)) {
                dropdown.style.display = "none";
                updateGuestDisplay();
            }
        });

        function updateGuestDisplay() {
            var totalGuests = guestCounts.adults + guestCounts.children;
            var infants = guestCounts.infants;
            
            // Create detailed breakdown
            var adultText = guestCounts.adults > 0 ? (guestCounts.adults + " adult" + (guestCounts.adults > 1 ? "s" : "")) : "";
            var childText = guestCounts.children > 0 ? (guestCounts.children + " " + (guestCounts.children > 1 ? "children" : "child")) : "";
            
            // Combine adult and child text with commas if both exist
            var details = "";
            if (adultText && childText) {
                details = adultText + ", " + childText;
            } else {
                details = adultText + childText;
            }
            
            // Main display text
            var display = totalGuests ? (totalGuests + " guest" + (totalGuests > 1 ? "s" : "") + (details ? " (" + details + ")" : "")) : "Add guests";
            
            // Add infants if any
            if (infants > 0) {
                display += ", " + infants + " infant" + (infants > 1 ? "s" : "");
            }
            
            guestInput.value = display;
            totalGuestsField.value = totalGuests;
        }

        document.querySelectorAll(".stepper-btn").forEach(btn => {
            btn.addEventListener("click", (e) => {
                // Prevent event from bubbling up
                e.stopPropagation();
                
                const parent = btn.closest(".guest-option");
                const type = parent.dataset.type;
                const number = parent.querySelector(".stepper-number");
                const decreaseBtn = parent.querySelector(".decrease");

                let totalGuests = guestCounts.adults + guestCounts.children;

                if (btn.classList.contains("increase")) {
                    if ((type === 'adults' || type === 'children') && totalGuests >= maxGuests) {
                        alert(`Maximum ${maxGuests} guests allowed.`);
                        return;
                    }
                    if (type === 'infants' && guestCounts.infants >= 5) {
                        alert("You can add up to 5 infants only.");
                        return;
                    }
                    guestCounts[type]++;
                } else {
                    if (guestCounts[type] > 0) {
                        if (type === 'adults' && guestCounts.adults === 1) {
                            alert("At least 1 adult is required.");
                            return;
                        }
                        guestCounts[type]--;
                    }
                }


                number.textContent = guestCounts[type];
                decreaseBtn.classList.toggle("disabled", guestCounts[type] === 0);
                updateGuestDisplay();
                calculateCharges();
            });
        });
    });
    document.getElementById("bookingForm").addEventListener("submit", function (e) {
        const checkin = new Date(document.getElementById("checkin").value);
        const checkout = new Date(document.getElementById("checkout").value);
        const guests = parseInt(document.getElementById("totalGuests").value, 10);
        const adults = guestCounts.adults;

        if (isNaN(checkin) || isNaN(checkout) || checkout <= checkin) {
            alert("Please select valid Check-in and Check-out dates.");
            e.preventDefault();
            return;
        }

        if (!guests || guests > maxGuests) {
            alert(`Please select up to ${maxGuests} guests.`);
            e.preventDefault();
            return;
        }

        if (adults < 1) {
            alert("At least 1 adult is required to make a booking.");
            e.preventDefault();
            return;
        }
    });

</script>

	<jsp:include page="footer.jsp" />
</body>
</html>