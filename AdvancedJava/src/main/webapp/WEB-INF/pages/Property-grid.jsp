<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 <c:choose>
        <c:when test="${not empty properties}">
            <c:forEach items="${properties}" var="property">
                <div class="hotel-card" data-category-ids="${property.categoryId}">
                    <!-- Image Gallery Container -->
                    <div class="hotel-image-gallery">
                        <!-- Main visible image -->
                        <div class="main-image-container">
                            <c:choose>
                                <c:when test="${not empty property.images}">
                                    <img src="${pageContext.request.contextPath}/resources/images/hotel/${property.images[0].fileName}" 
                                         alt="${property.propertyName}" 
                                         onerror="this.src='${pageContext.request.contextPath}/resources/images/hotel/default-img.jpg'"
                                         class="main-image">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/resources/images/hotel/default-img.jpg" 
                                         alt="Default property image"
                                         class="main-image">
                                </c:otherwise>
                            </c:choose>
                            <button class="wishlist-button" data-hotel-id="${property.propertyId}">
                                <i data-lucide="heart"></i>
                            </button>
                        </div>
                        
                        <!-- Thumbnail scrollbar (hidden by default) -->
                        <div class="thumbnail-scrollbar">
                            <div class="thumbnails-container">
                                <c:forEach items="${property.images}" var="image" varStatus="loop">
                                    <img src="${pageContext.request.contextPath}/resources/images/hotel/${image.fileName}" 
                                         alt="Thumbnail ${loop.index + 1}"
                                         onerror="this.src='${pageContext.request.contextPath}/resources/images/hotel/default-img.jpg'"
                                         class="thumbnail"
                                         data-full-image="${pageContext.request.contextPath}/resources/images/hotel/${image.fileName}">
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    
                    
                        <div class="hotel-info">
                            <h3>${property.propertyName}</h3>
                            <p>${property.propertyCity}, ${property.propertyCountry}</p>
                            <div class="hotel-footer">
                                <div class="price">
                                    <span class="amount">$${String.format("%.2f", property.pricePerNight)}</span>
                                    <span class="per-night">/night</span>
                                    <form action="${pageContext.request.contextPath}/BookingController" method="get">
                                        <input type="hidden" name="propertyId" value="${property.propertyId}">
                                        <button type="submit" class="book-button">Book Now</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="no-properties">
                    <p>No properties available at the moment.</p>
                </div>
            </c:otherwise>
        </c:choose>
        <script>
    document.addEventListener('DOMContentLoaded', function() {
        // Initialize Lucide icons
        lucide.createIcons();
        
        // Handle thumbnail clicks to change main image
        document.querySelectorAll('.thumbnail').forEach(thumb => {
            thumb.addEventListener('click', function() {
                const fullImageUrl = this.getAttribute('data-full-image');
                const mainImage = this.closest('.hotel-image-gallery').querySelector('.main-image');
                
                if (mainImage) {
                    mainImage.src = fullImageUrl;
                }
            });
        });
    });
    </script>