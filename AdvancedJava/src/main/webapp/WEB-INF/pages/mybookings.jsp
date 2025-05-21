<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mybookings.css" />
<header>
<jsp:include page="header.jsp" />
</header>
<body>
<div class="container">
    <div class="my-bookings">
        <h1 style="margin-bottom:20px">My Bookings</h1>
        
    </div>
    
<c:forEach var="booking" items="${bookings}" varStatus="status">
  <div class="booking-card">
    <div class="booking-header">
      <h2 class="booking-title">${properties[status.index].propertyName}</h2>
    </div>
    
    <div class="booking-content">
      <div class="booking-image-container">
        <div class="hotel-image-gallery">
          <!-- Main visible image -->
          <div class="main-image-container">
            <c:choose>
              <c:when test="${not empty properties[status.index].images && properties[status.index].images.size() > 0}">
                <img src="${pageContext.request.contextPath}/resources/images/property/${properties[status.index].images[0].fileName}" 
                     alt="${properties[status.index].propertyName}" 
                     onerror="this.src='${pageContext.request.contextPath}/resources/images/property/default-img.jpg'"
                     class="booking-image">
              </c:when>
              <c:otherwise>
                <img src="${pageContext.request.contextPath}/resources/images/property/default-img.jpg" 
                     alt="Property Image" 
                     class="booking-image">
              </c:otherwise>
            </c:choose>
          </div>
          
          <!-- Thumbnail scrollbar -->
          <c:if test="${not empty properties[status.index].images && properties[status.index].images.size() > 1}">
            <div class="thumbnail-scrollbar">
              <div class="thumbnails-container">
                <c:forEach items="${properties[status.index].images}" var="image" varStatus="loop">
                  <img src="${pageContext.request.contextPath}/resources/images/property/${image.fileName}"
                       alt="Thumbnail ${loop.index + 1}"
                       onerror="this.src='${pageContext.request.contextPath}/resources/images/property/default-img.jpg'"
                       class="thumbnail"
                       data-full-image="${pageContext.request.contextPath}/resources/images/property/${image.fileName}">
                </c:forEach>
              </div>
            </div>
          </c:if>
        </div>
      </div>
      
      <div class="booking-info">
        <div class="location">${properties[status.index].propertyCity}, ${properties[status.index].propertyCountry}</div>
        
        <div class="info-grid">
          <div class="info-item">
            <span class="info-label">Check-in</span>
            <span class="info-value"><fmt:formatDate pattern="MMM dd, yyyy" value="${booking.checkInDate}" /></span>
          </div>
          
          <div class="info-item">
            <span class="info-label">Check-out</span>
            <span class="info-value"><fmt:formatDate pattern="MMM dd, yyyy" value="${booking.checkOutDate}" /></span>
          </div>
          
          <div class="info-item">
            <span class="info-label">Guests</span>
            <span class="info-value">${booking.numberOfGuests}</span>
          </div>
          
          <div class="info-item">
            <span class="info-label">Booked on</span>
            <span class="info-value"><fmt:formatDate pattern="MMM dd, yyyy" value="${booking.bookingCreatedAt}" /></span>
          </div>
        </div>
      </div>
    </div>
    
    <div class="booking-footer">
      <div class="booking-status ${booking.bookingStatus.toString().toLowerCase()}">${booking.bookingStatus}</div>
      <div class="booking-actions">
        <form action="${pageContext.request.contextPath}/cancel-booking" method="post">
          <input type="hidden" name="bookingId" value="${booking.bookingId}">
          <button type="submit" class="btn btn-cancel">Cancel Booking</button>
        </form>
        
        <c:if test="${booking.bookingStatus == 'pending'}">
          <form action="${pageContext.request.contextPath}/process-payment" method="post">
            <input type="hidden" name="bookingId" value="${booking.bookingId}">
            <button type="submit" class="btn btn-pay">Pay Now</button>
          </form>
        </c:if>
      </div>
    </div>
  </div>
</c:forEach>

<!-- Include the JavaScript for the image gallery -->
<script>
  // Add JavaScript to handle thumbnail click events
  document.addEventListener('DOMContentLoaded', function() {
    // Find all thumbnail images
    var thumbnails = document.querySelectorAll('.thumbnail');
    
    // Add click event listener to each thumbnail
    thumbnails.forEach(function(thumbnail) {
      thumbnail.addEventListener('click', function() {
        // Get the full image URL from data attribute
        var fullImageSrc = this.getAttribute('data-full-image');
        
        // Find the closest main image to this thumbnail
        var mainImageContainer = this.closest('.hotel-image-gallery').querySelector('.main-image-container img');
        
        // Update the main image source
        if (mainImageContainer) {
          mainImageContainer.src = fullImageSrc;
        }
        
        // Remove active class from all thumbnails in this gallery
        var galleryThumbnails = this.closest('.thumbnails-container').querySelectorAll('.thumbnail');
        galleryThumbnails.forEach(function(thumb) {
          thumb.classList.remove('active');
        });
        
        // Add active class to clicked thumbnail
        this.classList.add('active');
      });
    });
    
    // Set the first thumbnail as active by default for each booking card
    var bookingCards = document.querySelectorAll('.booking-card');
    bookingCards.forEach(function(card) {
      var firstThumbnail = card.querySelector('.thumbnail');
      if (firstThumbnail) {
        firstThumbnail.classList.add('active');
      }
    });
  });
</script>

<!-- Rest of your HTML document -->
 </div>
<!-- Rest of your HTML document -->
</body>
</html>