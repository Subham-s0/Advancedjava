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
<!--images-->
<div class="gallery">
        <div class="item big"><img src="${pageContext.request.contextPath}/resources/images/hotel/img.avif" alt="Big Image"></div>
        <div class="item"><img src="${pageContext.request.contextPath}/resources/images/hotel/img2.jpg" alt="Small Image"></div>
        <div class="item"><img src="${pageContext.request.contextPath}/resources/images/hotel/img3.jpg" alt="Small Image"></div>
        <div class="item"><img src="${pageContext.request.contextPath}/resources/images/hotel/img4.jpg" alt="Small Image"></div>
        <div class="item"><img src="${pageContext.request.contextPath}/resources/images/hotel/img5.jpg" alt="Small Image"></div>
        <div class="item"><img src="${pageContext.request.contextPath}/resources/images/hotel/img6.jpg" alt="Small Image"></div>
        <div class="item"><img src="${pageContext.request.contextPath}/resources/images/hotel/hotel2.jpg" alt="Small Image"></div>
    </div>

    <div class="tabs">
        <button class="tab active">Overview</button>
        <button class="tab">Facilities</button>
        <button class="tab">Review</button>
    </div>

    <div class="container">
        <div class="hotel-info">
            <h2>${selectedProperty.propertyName}</h2>
            <p class="location">,${selectedProperty.propertyCity},${property.propertyCountry}</p>
            <h3>About hotel</h3>
            <p class="about">
                <strong>Landmark Hotel:</strong> exceptional value for two travelers.
                Free Wi-Fi, parking, and private balconies with city views. Near
                Siam Center and MBK Center, explore Bangkok easily with nearby
                attractions just a stone's throw away. Discover the vibrant
                neighborhood of Siam in Bangkok, known for trendy shopping malls,
                cultural attractions, and bustling street food scene. Enjoy absolute
                comfort with the 24-hour front desk, cozy coffee shop, and daily housekeeping.
                The air-conditioned rooms offer complimentary Wi-Fi, rejuvenating showers,
                and refreshing toiletries. Explore iconic landmarks like MBK Center,
                Victory Monument, and Siam Paragon, just a short distance away.
                Make your stay in Bangkok unforgettable at Diamond Bangkok Apartment.
                Some content may be Generative AI assisted. Inaccuracies may occur.
            </p>

            <div class="facilities">
                <h3>Facilities</h3>
                <ul>
                    <li>✔️ Free WIFI</li>
                    <li>✔️ Free Parking</li>
                    <li>✔️ Non-smoking room</li>
                    <li>✔️ Daily housekeeping</li>
                    <li>✔️ Taxi services</li>
                    <li>✔️ Balcony</li>
                </ul>
            </div>
        </div>

        <div class="price-box">
            <h2>$ 35/ Night</h2>
            <div class="dates">
                <div>
                    <label>Check-in</label><br>
                    <input type="text" placeholder="DD/MM/YYYY">
                </div>
                <div>
                    <label>Check-out</label><br>
                    <input type="text" placeholder="DD/MM/YYYY">
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
                    <span>35 * total nights</span>
                    <span>$35</span>
                </div>
                <div class="charge-line">
                    <span>Cleaning charge</span>
                    <span>$3</span>
                </div>
                <div class="charge-line">
                    <span>NestAway Service Charges</span>
                    <span>$10</span>
                </div>
                <div class="charge-line">
                    <span>Subtotal (Before Tax)</span>
                    <span>$48</span>
                </div>
                <div class="charge-line">
                    <span>Tax (10%)</span>
                    <span>$4.80</span>
                </div>
                <div class="charge-line total">
                    <span><strong>Total charges</strong></span>
                    <span><strong>$54.80</strong></span>
                </div>
            </div>


            <div class="buttons">
                <button class="add-to-cart">ADD TO CART</button>
                <button class="book-now">BOOK NOW</button>
            </div>
        </div>
    </div>
<jsp:include page="footer.jsp" />
</body>
</html>