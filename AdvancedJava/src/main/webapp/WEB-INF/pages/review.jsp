<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
  <%@ page import="com.Advancedjava.util.Sessionutil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <style>
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
    overflow-x: hidden;
    background: linear-gradient(135deg, #f3f4f6 0%, #e0e7ff 100%);
}

.container {
    max-width: 1200px;
    margin: 120px auto 50px;
    display: flex;
    flex-wrap: wrap;
    gap: 80px;
    padding: 0 20px;
    align-items: center;
}

.booking-summary {
    flex: 1 1 420px;
    min-width: 320px;
    max-width: 500px;
    background: #f9fbfd;
    border-radius: 12px;
    box-shadow: 0 4px 24px rgba(0,0,0,0.08);
    padding: 32px 28px 24px 28px;
    order: 1;
}

.booking-summary h1 {
    font-size: 1.8rem;
    color: #222;
    margin-bottom: 24px;
    font-weight: 600;
}

.review-section {
    display: flex;
    flex-direction: column;
    flex: 1 1 420px;
    min-width: 320px;
    max-width: 500px;
    order: 2;
    background: #f9fbfd;
    border-radius: 16px;
}

.review-heading {
    margin-bottom: 8px;
    font-size: 2rem;
    color: #222;
    text-align: center;
    font-weight: 600;
}

.review-section > p {
    text-align: center;
    color: #666;
    margin-bottom: 24px;
    font-size: 1.1rem;
}

.review-box {
    width: 100%;
     background: #f9fbfd;
    box-shadow: 0 4px 24px rgba(0,0,0,0.08);
    padding: 32px 28px 24px 28px;
}

.booking-details {
    border-bottom: 1px solid #eee;
    padding-bottom: 20px;
    margin-bottom: 24px;
}

.booking-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 12px;
}

.booking-label {
    color: #666;
    font-size: 1rem;
    font-weight: 500;
}

.booking-value {
    font-weight: 600;
    font-size: 1rem;
    color: #222;
}

/* Fixed image container styling */
.main-image-container {
    width: 100%;
    height: 200px;
    margin: 20px 0;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.main-image-container img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 12px;
    transition: transform 0.3s ease;
}

.main-image-container img:hover {
    transform: scale(1.02);
}

.price-total {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-weight: 700;
    font-size: 1.5rem;
    margin-top: 20px;
    padding-top: 20px;
    border-top: 2px solid #eee;
    color: #222;
}

.price-total span:last-child {
    color: #444444;
}

.review-box h2 {
    margin-bottom: 18px;
    font-size: 1.5rem;
    color: #222;
    text-align: center;
    font-weight: 600;
}

.star-rating {
    display: flex;
    flex-direction: row-reverse;
    justify-content: center;
    gap: 8px;
    font-size: 2.5rem;
    margin-bottom: 20px;
}

.star-rating input[type="radio"] {
    display: none;
}

.star-rating label {
    color: #e0e0e0;
    cursor: pointer;
    transition: color 0.3s ease, transform 0.2s ease;
}

.star-rating label:hover {
    transform: scale(1.1);
}

.star-rating input[type="radio"]:checked ~ label,
.star-rating label:hover,
.star-rating label:hover ~ label {
    color: #ffc107;
}

textarea {
    width: 100%;
    min-height: 100px;
    padding: 12px;
    border: 1.5px solid #d1d5db;
    border-radius: 8px;
    font-size: 1rem;
    font-family: inherit;
    resize: vertical;
    margin-bottom: 20px;
    transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

textarea:focus {
    border-color: #0078d4;
    outline: none;
    box-shadow: 0 0 0 3px rgba(0, 120, 212, 0.1);
}

textarea::placeholder {
    color: #999;
}

button {
    width: 100%;
    padding: 14px 0;
   
    background: linear-gradient(90deg, #2d2d38 0%, #1787d7 100%);
    color: #fff;
    border: none;
    border-radius: 8px;
    font-size: 1.1rem;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 4px 12px rgba(0,120,212,0.2);
}

button:hover, 
button:focus {
    background: linear-gradient(90deg, #005fa3 0%, #0078d4 100%);
    transform: translateY(-2px);
    box-shadow: 0 6px 16px rgba(0,120,212,0.3);
}

button:active {
    transform: translateY(0);
}

/* Error and Success Messages */
.error-message {
    position: fixed;
    top: 80px;
    right: 20px;
    background: #fee;
    border: 1px solid #fcc;
    border-radius: 8px;
    padding: 12px 16px;
    max-width: 300px;
    opacity: 0;
    transform: translateX(100%);
    transition: all 0.3s ease;
    z-index: 1000;
}

.error-message.visible {
    opacity: 1;
    transform: translateX(0);
}

.success-message {
    position: fixed;
    top: 80px;
    right: 20px;
    background: #efe;
    border: 1px solid #cfc;
    border-radius: 8px;
    padding: 12px 16px;
    max-width: 300px;
    opacity: 0;
    transform: translateX(100%);
    transition: all 0.3s ease;
    z-index: 1000;
}

.success-message.visible {
    opacity: 1;
    transform: translateX(0);
}

.success-message p {
    color: #060;
    margin: 0;
    font-weight: 500;
}

/* Responsive Design */
@media (max-width: 1100px) {
    .container {
        flex-direction: column;
        align-items: stretch;
        gap: 24px;
    }
    
    .booking-summary,
    .review-section {
        max-width: 100%;
        order: unset;
    }
    
    .booking-summary {
        margin-bottom: 0;
    }
}

@media (max-width: 768px) {
    .container {
        margin: 100px auto 30px;
        padding: 0 15px;
    }
    
    .booking-summary,
    .review-box {
        padding: 24px 20px;
    }
    
    .review-heading {
        font-size: 1.6rem;
    }
    
    .star-rating {
        font-size: 2rem;
        gap: 6px;
    }
    
    .main-image-container {
        height: 180px;
        margin: 16px 0;
    }
    
    .price-total {
        font-size: 1.3rem;
    }
}

@media (max-width: 480px) {
    .booking-item {
        flex-direction: column;
        align-items: flex-start;
        gap: 4px;
    }
    
    .booking-value {
        font-size: 0.95rem;
    }
    
    .star-rating {
        font-size: 1.8rem;
    }
    
    .main-image-container {
        height: 160px;
    }
}
}
    </style>
</head>
<header><jsp:include page="header.jsp" /></header>
<body>


 <div class="container">
        
         <%	String error = (String) session.getAttribute("error");	%>

 <div class="error-message <%=(error != null && !error.isEmpty()) ? "visible" : ""%>">
					<c:if test="${not empty error}">
						<p style="color: red;">
							<c:out value="${error}" />
						</p>
					</c:if>

				</div>
				 <% String success = (String) session.getAttribute("success"); %>
    <div class="success-message <%= (success != null && !success.isEmpty()) ? "visible" : "" %>">
        <%
            if (success != null && !success.isEmpty()) {
        %>
            <p><%= success %></p>
        <%
            }
            Sessionutil.removeAttribute(request, "success");
            Sessionutil.removeAttribute(request, "error");
        %>
    </div>
        
            <div class="booking-summary">
        <h1>Booking Summary</h1>
        <div class="booking-details">
            <div class="booking-item">
                <span class="booking-label">Booking ID</span>
                <span class="booking-value">BK-${booking.bookingId}</span>
            </div>
            <div class="booking-item">
                <span class="booking-label">Check-in</span>
                <span class="booking-value">
                    <fmt:formatDate value="${booking.checkInDate}" pattern="MMM dd, yyyy" />
                </span>
            </div>
            <div class="booking-item">
                <span class="booking-label">Check-out</span>
                <span class="booking-value"><fmt:formatDate value="${booking.checkOutDate}" pattern="MMM dd, yyyy" /></span>
            </div>
            <div class="booking-item">
                <span class="booking-label">Guests</span>
                <span class="booking-value">${booking.numberOfGuests} Guests</span>
            </div>
            <div class="booking-item">
                <span class="booking-label">Property</span>
                <span class="booking-value">${property.propertyName}</span>
            </div>
        </div>
        <div class="main-image-container">
                            <c:choose>
                            
                                <c:when test="${not empty property.images}">
                                    <img src="${pageContext.request.contextPath}/resources/images/property/${property.images[0].fileName}" 
                                         alt="${property.propertyName}" 
                                         onerror="this.src='${pageContext.request.contextPath}/resources/images/property/default-img.jpg'"
                                         class="main-image">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/resources/images/property/default-img.jpg" 
                                         alt="Default property image"
                                         class="main-image">
                                </c:otherwise>
                            </c:choose>
                            </div>
        <div class="price-total">
            <span>Total</span>
            <span>$<fmt:formatNumber value="${totalPrice}" pattern="#,##0.00" /></span>
        </div>
    </div>
    <div class="review-section">
        <div class="review-heading">Complete your Review</div>
        <p style="text-align:center;">Give your feedback</p>
        <div class="review-box">
            <h2>Submit Your Review</h2>
            <form action="review" method="post">
                <input type="hidden" name="bookingId" value="<%= request.getParameter("bookingId") %>" />
                <div class="star-rating">
                    <input type="radio" id="star5" name="rating" value="5"><label for="star5">&#9733;</label>
                    <input type="radio" id="star4" name="rating" value="4"><label for="star4">&#9733;</label>
                    <input type="radio" id="star3" name="rating" value="3"><label for="star3">&#9733;</label>
                    <input type="radio" id="star2" name="rating" value="2"><label for="star2">&#9733;</label>
                    <input type="radio" id="star1" name="rating" value="1"><label for="star1">&#9733;</label>
                </div>
                <textarea name="comment" rows="4" placeholder="Write your review..."></textarea>
                <button type="submit">Submit Review</button>
            </form>
        </div>
    </div>
</div>
     <jsp:include page="footer.jsp" />
</body>
</html>