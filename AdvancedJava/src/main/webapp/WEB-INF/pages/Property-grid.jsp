<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://unpkg.com/lucide@latest/dist/lucide.css" />
<style>
  /* Hotel Image Gallery Styles */
    .hotel-image-gallery {
        position: relative;
        height: 250px;
        overflow: hidden;
    }
   

.hotel-card {
    background: white;
    border-radius: 16px;
    overflow: hidden;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    transition: transform 0.2s ease;
}

.hotel-card:hover {
    transform: translateY(-4px);
}

.hotel-image {
    position: relative;
    padding-top: 75%;
}

.hotel-image img {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.wishlist-button {
    position: absolute;
    top: 12px;
    right: 12px;
    width: 48px;
    height: 48px;
    border-radius: 50%;
    background-color: transparent;
    border: none;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: all 0.2s ease;
    padding: 4px;
}

.wishlist-button svg:hover {
	padding:none;
    fill: rgba(0, 0, 0, 0);
}

/* Default Lucide icon style */



.hotel-info {
    padding: 16px;
}

.hotel-info h3 {
    margin: 0 0 8px;
    font-size: 18px;
    font-weight: 600;
}

.hotel-info p {
    margin: 0 0 16px;
    color: #666;
    font-size: 14px;
}

.hotel-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.price {
    display: flex;
    align-items: baseline;
}

.amount {
    font-size: 20px;
    font-weight: 600;
}

.per-night {
    font-size: 14px;
    color: #666;
    margin: 0px 10px;
}

.book-button {
    padding: 8px 16px;
    background: #222;
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: all 0.2s ease;
}
.book-button:hover {
    background:rgb(0, 255, 255);
    color:#222;
    transform: scale(1.05);
}
    
    .main-image-container {
        position: relative;
        height: 100%;
    }
    
    .main-image-container img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        transition: transform 0.3s ease;
    }
    
    .thumbnail-scrollbar {
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        height: 45px;
        background: rgba(0,0,0,0.5);
        overflow-x: auto;
        overflow-y: hidden;
        display: none; /* Hidden by default */
        transition: all 0.3s ease;
    }
    
    .thumbnails-container {
        display: flex;
        height: 100%;
        padding: 0px;
        gap: 5px;
    }
    
    .thumbnail {
        height: 100%;
        width: auto;
        cursor: pointer;
        transition: opacity 0.2s ease;
        opacity:0.6;
    }
    
    .thumbnail:hover {
        opacity:1;
    }
    
    /* Show thumbnails on hover */
    .hotel-image-gallery:hover .thumbnail-scrollbar {
        display: block;
    }
    
    .thumbnail-scrollbar::-webkit-scrollbar {
        height: 5px;
    }
    
    .thumbnail-scrollbar::-webkit-scrollbar-thumb {
        background: rgba(255,255,255,0.5);
    }
</style>
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
                           <c:set var="inWishlist" value="${wishlistIds.contains(property.propertyId)}" />
							
							<!-- Wishlist Button Form -->
							<form action="${pageContext.request.contextPath}/WishListController" method="post">
							    <input type="hidden" name="propertyId" value="${property.propertyId}" />
							    <button class="wishlist-button ${inWishlist ? 'in-wishlist' : 'not-in-wishlist'}" data-hotel-id="${property.propertyId}">
							        <svg xmlns="http://www.w3.org/2000/svg"
							        width="28" 
							        height="28"
							        viewBox="0 0 24 24"
							        fill="${inWishlist ? '#ff474c' : 'rgba(0, 0, 0, 0.5)'}"
							        stroke="white"
							        stroke-width="1.7"
							        stroke-linecap="round"
							        stroke-linejoin="round"
							        class="lucide lucide-heart">
							        <path d="M12 21.35L10.55 20.03C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"/></svg>
							    </button>
							</form>
                        </div>
                        
                        <!-- Thumbnail scrollbar (hidden by default) -->
                        <div class="thumbnail-scrollbar">
                            <div class="thumbnails-container">
                                <c:forEach items="${property.images}" var="image" varStatus="loop">
                                    <img src="${pageContext.request.contextPath}/resources/images/property/${image.fileName}" 
                                         alt="Thumbnail ${loop.index + 1}"
                                         onerror="this.src='${pageContext.request.contextPath}/resources/images/property/default-img.jpg'"
                                         class="thumbnail"
                                         data-full-image="${pageContext.request.contextPath}/resources/images/property/${image.fileName}">
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