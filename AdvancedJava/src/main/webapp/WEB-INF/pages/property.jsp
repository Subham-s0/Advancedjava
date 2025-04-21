<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/property.css" />
<title>Insert title here</title><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/Searchbar.css" />
</head>
<body>
<header >
<jsp:include page="header.jsp" />

</header>
<div class="search-container">
	            <jsp:include page="searchbar.jsp" />
	         </div>

<section class="hotels container">
        <h2>Featured Luxury Properties</h2>
          <div class="hotel-grid">
            <div class="hotel-card">
                <div class="hotel-image">
                    <img src="https://images.pexels.com/photos/271624/pexels-photo-271624.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
                        alt="Premium City View Suite">
                    <button class="favorite-btn" aria-label="Add to favorites">♡</button>
                </div>
                <div class="hotel-info">
                    <h3>Premium City View Suite</h3>
                    <p class="location">Downtown Manhattan, NY</p>
                    <div class="price-book">
                        <div class="price">
                            <span class="amount">$299</span>
                            <span class="period">/night</span>
                        </div>
                        <button class="btn btn-primary btn-sm">Book Now</button>
                    </div>
                </div>
            </div>

            <div class="hotel-card">
                <div class="hotel-image">
                    <img src="https://images.pexels.com/photos/2506990/pexels-photo-2506990.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
                        alt="Oceanfront Paradise Villa">
                    <button class="favorite-btn" aria-label="Add to favorites">♡</button>
                </div>
                <div class="hotel-info">
                    <h3>Oceanfront Paradise Villa</h3>
                    <p class="location">Miami Beach, FL</p>
                    <div class="price-book">
                        <div class="price">
                            <span class="amount">$459</span>
                            <span class="period">/night</span>
                        </div>
                        <button class="btn btn-primary btn-sm">Book Now</button>
                    </div>
                </div>
            </div>

            <div class="hotel-card">
                <div class="hotel-image">
                    <img src="https://images.pexels.com/photos/261102/pexels-photo-261102.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
                        alt="Alpine Mountain Retreat">
                    <button class="favorite-btn" aria-label="Add to favorites">♡</button>
                </div>
                <div class="hotel-info">
                    <h3>Alpine Mountain Retreat</h3>
                    <p class="location">Swiss Alps, Switzerland</p>
                    <div class="price-book">
                        <div class="price">
                            <span class="amount">$599</span>
                            <span class="period">/night</span>
                        </div>
                        <button class="btn btn-primary btn-sm">Book Now</button>
                    </div>
                </div>
            </div>

            <div class="hotel-card">
                <div class="hotel-image">
                    <img src="https://images.pexels.com/photos/258154/pexels-photo-258154.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
                        alt="Alpine Mountain Retreat">
                    <button class="favorite-btn" aria-label="Add to favorites">♡</button>
                </div>
                <div class="hotel-info">
                    <h3>Alpine Mountain Retreat</h3>
                    <p class="location">Swiss Alps, Switzerland</p>
                    <div class="price-book">
                        <div class="price">
                            <span class="amount">$599</span>
                            <span class="period">/night</span>
                        </div>
                        <button class="btn btn-primary btn-sm">Book Now</button>
                    </div>
                </div>
            </div>
            <!-- Row 2 -->
        <div class="hotel-card">
            <div class="hotel-image">
                <img src="https://images.pexels.com/photos/164595/pexels-photo-164595.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
                    alt="Luxury Beach Resort">
                <button class="favorite-btn" aria-label="Add to favorites">♡</button>
            </div>
            <div class="hotel-info">
                <h3>Luxury Beach Resort</h3>
                <p class="location">Maldives</p>
                <div class="price-book">
                    <div class="price">
                        <span class="amount">$799</span>
                        <span class="period">/night</span>
                    </div>
                    <button class="btn btn-primary btn-sm">Book Now</button>
                </div>
            </div>
        </div>

        <div class="hotel-card">
            <div class="hotel-image">
                <img src="https://images.pexels.com/photos/261102/pexels-photo-261102.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
                    alt="Mountain Escape">
                <button class="favorite-btn" aria-label="Add to favorites">♡</button>
            </div>
            <div class="hotel-info">
                <h3>Mountain Escape</h3>
                <p class="location">Aspen, CO</p>
                <div class="price-book">
                    <div class="price">
                        <span class="amount">$499</span>
                        <span class="period">/night</span>
                    </div>
                    <button class="btn btn-primary btn-sm">Book Now</button>
                </div>
            </div>
        </div>

        <div class="hotel-card">
            <div class="hotel-image">
                <img src="https://images.pexels.com/photos/271624/pexels-photo-271624.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
                    alt="City Lights Penthouse">
                <button class="favorite-btn" aria-label="Add to favorites">♡</button>
            </div>
            <div class="hotel-info">
                <h3>City Lights Penthouse</h3>
                <p class="location">Tokyo, Japan</p>
                <div class="price-book">
                    <div class="price">
                        <span class="amount">$699</span>
                        <span class="period">/night</span>
                    </div>
                    <button class="btn btn-primary btn-sm">Book Now</button>
                </div>
            </div>
        </div>

        <div class="hotel-card">
            <div class="hotel-image">
                <img src="https://images.pexels.com/photos/2506990/pexels-photo-2506990.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"
                    alt="Oceanfront Paradise">
                <button class="favorite-btn" aria-label="Add to favorites">♡</button>
            </div>
            <div class="hotel-info">
                <h3>Oceanfront Paradise</h3>
                <p class="location">Bali, Indonesia</p>
                <div class="price-book">
                    <div class="price">
                        <span class="amount">$399</span>
                        <span class="period">/night</span>
                    </div>
                    <button class="btn btn-primary btn-sm">Book Now</button>
                </div>
            </div>
        </div>
        
        </div>
        
    </section>
    <script src="${pageContext.request.contextPath}/javascript/Searchbar.js"></script>

</body>
</html>