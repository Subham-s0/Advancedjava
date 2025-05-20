<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nestaway</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/propertydescription.css" />
</head>
<body>
<header>
<jsp:include page="header.jsp" />
</header>
	<div class="top">
        <div class="gallery">
        <div class="big-image" id="main-image"><img src="${pageContext.request.contextPath}/resources/images/hotel/img3.jpg" alt="Big Image"alt="Big Image"></div>
             <div class="small-images">
                <img class="thumb" src="${pageContext.request.contextPath}/resources/images/hotel/img2.jpg" alt="Small Image">
                <img class="thumb" src="${pageContext.request.contextPath}/resources/images/hotel/img.avif" alt="Small Image">
                <img class="thumb" src="${pageContext.request.contextPath}/resources/images/hotel/img4.jpg" alt="Small Image">
                <img class="thumb" src="${pageContext.request.contextPath}/resources/images/hotel/img5.jpg" alt="Small Image">
                <img class="thumb" src="${pageContext.request.contextPath}/resources/images/hotel/img6.jpg" alt="Small Image">
                <img class="thumb" src="${pageContext.request.contextPath}/resources/images/hotel/img2.jpg" alt="Small Image">
            </div>
            </div>
            <div class="price-box">
            <h2>$ ${selectedProperty.pricePerNight} \ Night</h2>
            <div class="dates">
                <div>
                    <label>Check-in</label><br>
                    <input type="date" id="checkin" placeholder="DD/MM/YYYY">
                </div>
                <div>
                    <label>Check-out</label><br>
                    <input type="date" id="checkout" placeholder="DD/MM/YYYY">
                </div>
            </div>
             <div class="guests">
                <label>Guests</label><br>
                <select>
                    <option>2 Adults</option>
                    <option>1 Adult</option>
                    <option>Family</option>
                </select>
            </div>
            <div class="charges">
                <div class="charge-line">
                    <span>Charge per night</span>
                    <span id="charge-per-night">$35</span>
                </div>
                <div class="charge-line">
                    <span>Cleaning charge</span>
                    <span id="cleaning-charge">$3</span>
                </div>
                <div class="charge-line">
                    <span>Service charge</span>
                    <span id="service-charge">$10</span>
                </div>
                <div class="charge-line">
                    <span>Subtotal (Before Tax)</span>
                    <span id="subtotal">$0</span>
                </div>
                <div class="charge-line">
                    <span>Tax (10%)</span>
                    <span id="tax">$0</span>
                </div>
                <div class="charge-line total">
                    <span><strong>Total charges</strong></span>
                    <span id="total-charges"><strong>$0</strong></span>
                </div>
            </div>

            <div class="buttons">
                <button class="add-to-cart">ADD TO CART</button>
                <button class="book-now">BOOK NOW</button>
            </div>
        </div>
    </div>
          
    <div class="tabs">
        <button class="tab active" id="overviewBtn">Overview</button>
        <button class="tab" id="facilityBtn">Facilities</button>
        <button class="tab" id="reviewBtn">Review</button>
    </div>
    
    <div class="container">
        <!-- Overview Section -->
        <div class="hotel-info" id="overview">
            <h2>${selectedProperty.propertyName}</h2>
            <p class="location">${selectedProperty.propertyCity}, ${selectedProperty.propertyCountry}</p>
            <h3>About hotel</h3>
            <p class="about">
                <strong>${selectedProperty.propertyDescription}</strong> 
            </p>

 <!-- Facilities section is part of the Overview -->
            <section id="facility-overview">
                <div class="facilities">
                    <h3>Facilities</h3>
                    <ul>
                        <li>✔ Free WIFI</li>
                        <li>✔ Free Parking</li>
                        <li>✔ Non-smoking room</li>
                        <li>✔ Daily housekeeping</li>
                        <li>✔ Taxi services</li>
                        <li>✔ Balcony</li>
                    </ul>
                </div>
            </section>
            <!-- Review Section -->
            <section id="reviews" class="reviews">
                <h3>What Our Guests Say</h3>
                <div class="review-cards">
                    <div class="review-card">
                        <h4>Subham S.</h4>
                        <p class="role">Software Developer</p>
                        <p class="review-text">
                            "Landmark Hotel has been a game-changer for my business trips. The service is excellent, and
                            the facilities are top-notch!"
                        </p>
                        <p class="rating">⭐ 4.9/5</p>
                    </div>
                    <div class="review-card">
                        <h4>Nabin B.</h4>
                        <p class="role">Freelance Artist</p>
                        <p class="review-text">
                            "The rooms are spacious, and the staff is very friendly. I loved the balcony view and the
                            daily housekeeping service!"
                        </p>
                        <p class="rating">⭐ 4.8/5</p>
                    </div>
                    <div class="review-card">
                        <h4>Megha A.</h4>
                        <p class="role">Student</p>
                        <p class="review-text">
                            "Affordable and comfortable stay. The free Wi-Fi and parking were a huge plus for me. Highly
                            recommend!"
                        </p>
                        <p class="rating">⭐ 4.7/5</p>
                    </div>
                </div>
            </section>
        </div>

        <!-- Facilities Section (Standalone) -->
        <section id="facility" style="display: none;">
            <div class="facilities">
                <h3>Facilities</h3>
                <ul>
                    <li>✔ Free WIFI</li>
                    <li>✔ Free Parking</li>
                    <li>✔ Non-smoking room</li>
                    <li>✔ Daily housekeeping</li>
                    <li>✔ Taxi services</li>
                    <li>✔ Balcony</li>
                </ul>
            </div>
        </section>
    </div>


    <script src="https://unpkg.com/lucide@latest"></script>
    <script>
        lucide.createIcons();
        const thumbnails = document.querySelectorAll('.thumb');
        const mainImage = document.getElementById('main-image').querySelector('img');  // Access the img inside main-image div

        thumbnails.forEach(thumb => {
            thumb.addEventListener('click', () => {
                mainImage.src = thumb.src;  // Update the src of the image inside main-image
            });
        });


    </script>
    <script>
        // Example data from the database
        const chargePerNight = 35; // Base charge per night
        const cleaningCharge = 3; // Cleaning charge
        const serviceCharge = 10; // Service charge
        const taxRate = 0.10; // Tax rate (10%)

        // Function to calculate total charges
        function calculateCharges() {
            const checkinDate = new Date(document.getElementById('checkin').value);
            const checkoutDate = new Date(document.getElementById('checkout').value);
            const guests = document.querySelector('.guests select').value;

            // Check if both dates are valid
            if (isNaN(checkinDate) || isNaN(checkoutDate)) {
                // Do not proceed if either date is invalid
                return;
            }

            // Validate that Check-out date is later than Check-in date
            if (checkoutDate <= checkinDate) {
                document.getElementById('subtotal').textContent = '$0';
                document.getElementById('tax').textContent = '$0';
                document.getElementById('total-charges').textContent = '$0';
                alert("Check-out date must be later than Check-in date.");
                return;
            }

            // Calculate total nights
            const totalNights = (checkoutDate - checkinDate) / (1000 * 60 * 60 * 24);

            // Adjust charge per night based on the number of guests
            let adjustedChargePerNight = chargePerNight;
            if (guests === "1 Adult") {
                adjustedChargePerNight = chargePerNight; // No change for 1 Adult
            } else if (guests === "2 Adults") {
                adjustedChargePerNight = chargePerNight * 1.5; // 50% increase for 2 Adults
            } else if (guests === "Family") {
                adjustedChargePerNight = chargePerNight * 2; // Double the charge for Family
            }

            // Calculate subtotal
            const subtotal = (adjustedChargePerNight * totalNights) + cleaningCharge + serviceCharge;

            // Calculate tax
            const tax = subtotal * taxRate;

            // Calculate total charges
            const totalCharges = subtotal + tax;

            // Update the UI
            document.getElementById('subtotal').textContent = $${subtotal.toFixed(2)};
            document.getElementById('tax').textContent = $${tax.toFixed(2)};
            document.getElementById('total-charges').textContent = $${totalCharges.toFixed(2)};
        }

        // Add event listeners to calculate charges when inputs are changed
        document.getElementById('checkin').addEventListener('change', calculateCharges);
        document.getElementById('checkout').addEventListener('change', calculateCharges);
        document.querySelector('.guests select').addEventListener('change', calculateCharges);
    </script>
    <br>
    <script>
        // Get references to the buttons and sections
        const overviewBtn = document.getElementById('overviewBtn');
        const facilityBtn = document.getElementById('facilityBtn');
        const reviewBtn = document.getElementById('reviewBtn');
        const overviewSection = document.getElementById('overview');
        const facilitySection = document.getElementById('facility');
        const reviewSection = document.getElementById('reviews');

        // Function to handle tab switching
        function switchTab(activeBtn, inactiveBtns, showSection, hideSections) {
            // Update active tab
            activeBtn.classList.add('active');
            inactiveBtns.forEach(btn => btn.classList.remove('active'));

            // Show the selected section and hide others
            showSection.style.display = 'block';
            hideSections.forEach(section => section.style.display = 'none');
        }

        // Add event listener for the "Overview" button
        overviewBtn.addEventListener('click', function () {
            switchTab(overviewBtn, [facilityBtn, reviewBtn], overviewSection, [facilitySection, reviewSection]);
        });

        // Add event listener for the "Facilities" button
        facilityBtn.addEventListener('click', function () {
            switchTab(facilityBtn, [overviewBtn, reviewBtn], facilitySection, [overviewSection, reviewSection]);
        });

        // Add event listener for the "Review" button
        reviewBtn.addEventListener('click', function () {
            switchTab(reviewBtn, [overviewBtn, facilityBtn], reviewSection, [overviewSection, facilitySection]);
        });
    </script>
    <script>
        // Get references to the buttons
        const addToCartBtn = document.querySelector('.add-to-cart');
        const bookNowBtn = document.querySelector('.book-now');

        // Function to validate inputs
        function validateInputs() {
            const checkinDate = new Date(document.getElementById('checkin').value);
            const checkoutDate = new Date(document.getElementById('checkout').value);
            const guests = document.querySelector('.guests select').value;

            // Validate dates
            if (isNaN(checkinDate) || isNaN(checkoutDate) || checkoutDate <= checkinDate) {
                alert('Please select valid Check-in and Check-out dates.');
                return false;
            }

            // Validate guests
            if (!guests) {
                alert('Please select the number of guests.');
                return false;
            }

            return true; // All inputs are valid
        }

        // Add event listener for the "Add to Cart" button
        addToCartBtn.addEventListener('click', function () {
            if (validateInputs()) {
                alert('Item has been added to your cart!');
            }
        });

        // Add event listener for the "Book Now" button
        bookNowBtn.addEventListener('click', function () {
            if (validateInputs()) {
                alert('Thank you for booking! Your reservation is confirmed.');
            }
        });
    </script>
<jsp:include page="footer.jsp" />
</body>
</html>