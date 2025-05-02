<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NestAway</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/profile.css" />
</head>
<body>
 <jsp:include page="header.jsp" />
 <div class="main-container">
        <aside class="sidebar">
            <div class="user-info">
                <div class="user-avatar">
                    <img id="profileImage" src="${pageContext.request.contextPath}/Profile_pictureservlet" alt="User avatar">
                    <button class="photo-edit-btn" id="editPhotoBtn" aria-label="Edit profile photo" type = "submit">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"/>
                            <circle cx="12" cy="13" r="4"/>
                        </svg>
                    </button>
                </div>
                <h3>John Doe</h3>
                <p>john.doe@example.com</p>
            </div>
            <nav class="sidebar-nav">
                <a href="#personal-info" class="nav-item active">Personal Information</a>
                <a href="#bookings" class="nav-item">My Bookings</a>
                <a href="#wishlist" class="nav-item">My Wishlist</a>
                <a href="#change-password" class="nav-item">Change Password</a>
                <a href="${pageContext.request.contextPath}/logout" class="nav-item logout">Logout<i data-lucide="log-out"></i></a>
            </nav>
        </aside>

        <div class="content-area">
            <section id="personal-info" class="content-section">
                <h2>Personal Information</h2>
                <form class="profile-form" action="#" method="POST">
                    <div class="form-row">
                        <div class="form-group">
                        <label for="firstName">First Name</label>
                            <input type="text" id="firstName" name="firstName" value="John" required>
                            
                        </div>
                        <div class="form-group">
                         <label for="lastName">Last Name</label>
                           <input type="text" id="lastName" name="lastName" value="Doe" required>
                            
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                        <label for="username">Username</label>
                            <input type="text" id="username" name="username" value="johndoe123" required>
                           
                        </div>
                       <div class="form-group">
						    <label>Gender</label>
						    <div class="radio-group" >
						        <label class="radio-label">
						            <input type="radio" id="gender-male" name="gender" value="male" >
						            <span>Male</span>
						        </label>
						        <label class="radio-label">
						            <input type="radio" id="gender-female" name="gender" value="female">
						            <span>Female</span>
						        </label>
						    </div>
						</div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="dob">Date of Birth</label>
                            <input type="date" id="dob" name="dob" value="1990-01-15" required>
                        </div>
                        <div class="form-group">
                            <label for="email">Email Address</label>
                            <input type="email" id="email" name="email" value="john.doe@example.com" required>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="phone">Phone Number</label>
                            <input type="tel" id="phone" name="phone" value="+1 234 567 8900" required>
                        </div>
                       
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn-primary">Save Changes</button>
                        <button type="reset" class="btn-reset">Reset</button>
                    </div>
                </form>
            </section>

            <section id="bookings" class="content-section">
                <h2>My Bookings</h2>
                <div class="bookings-list">
                    <div class="booking-card">
                        <img src="https://images.pexels.com/photos/1488327/pexels-photo-1488327.jpeg?auto=compress&cs=tinysrgb&w=600" alt="Property">
                        <div class="booking-details">
                            <h3>Luxury Beach Villa</h3>
                            <p class="location">Miami, Florida</p>
                            <div class="booking-dates">
                                <span>Check-in: Mar 15, 2024</span>
                                <span>Check-out: Mar 20, 2024</span>
                            </div>
                            <div class="booking-status confirmed">Confirmed</div>
                        </div>
                    </div>

                    <div class="booking-card">
                        <img src="https://images.pexels.com/photos/1428348/pexels-photo-1428348.jpeg?auto=compress&cs=tinysrgb&w=600" alt="Property">
                        <div class="booking-details">
                            <h3>Mountain Cabin</h3>
                            <p class="location">Aspen, Colorado</p>
                            <div class="booking-dates">
                                <span>Check-in: Apr 10, 2024</span>
                                <span>Check-out: Apr 15, 2024</span>
                            </div>
                            <div class="booking-status pending">Pending</div>
                        </div>
                    </div>
                </div>
            </section>

            <section id="wishlist" class="content-section">
                <h2>My Wishlist</h2>
                <div class="wishlist-grid">
                    <div class="property-card">
                        <img src="https://images.pexels.com/photos/1396122/pexels-photo-1396122.jpeg?auto=compress&cs=tinysrgb&w=600" alt="Property">
                        <div class="property-details">
                            <h3>Seaside Resort</h3>
                            <p class="location">Malibu, California</p>
                            <p class="price">$350/night</p>
                            <button class="btn-secondary">View Property</button>
                        </div>
                    </div>

                    <div class="property-card">
                        <img src="https://images.pexels.com/photos/1438832/pexels-photo-1438832.jpeg?auto=compress&cs=tinysrgb&w=600" alt="Property">
                        <div class="property-details">
                            <h3>Downtown Loft</h3>
                            <p class="location">New York City</p>
                            <p class="price">$250/night</p>
                            <button class="btn-secondary">View Property</button>
                        </div>
                    </div>
                </div>
            </section>

            <section id="change-password" class="content-section change-password">
                <h2>Change Password</h2>
                <form class="profile-form" action="#" method="POST">
                    <div class="form-row">
                        <div class="form-group">
                            <label for="currentPassword">Current Password</label>
                            <input type="password" id="currentPassword" name="currentPassword" required>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label for="newPassword">New Password</label>
                            <input type="password" id="newPassword" name="newPassword" required>
                        </div>
                        <div class="form-group">
                            <label for="confirmPassword">Confirm New Password</label>
                            <input type="password" id="confirmPassword" name="confirmPassword" required>
                        </div>
                    </div>
                    <div class="form-actions">
                        <button type="submit" class="btn-primary">Update Password</button>
                    </div>
                </form>
            </section>
        </div>
    </div>

    <script>
    lucide.createIcons();
    document.addEventListener('DOMContentLoaded', function() {
        const navItems = document.querySelectorAll('.sidebar-nav .nav-item');
        const contentSections = document.querySelectorAll('.content-section');
        
        function showSection(sectionId) {
            contentSections.forEach(section => {
                section.style.display = section.id === sectionId ? 'block' : 'none';
            });
        }

        function updateActiveNav(clickedItem) {
            navItems.forEach(item => {
                item.classList.toggle('active', item === clickedItem);
            });
        }

        navItems.forEach(item => {
            item.addEventListener('click', function(e) {
                // Only prevent default for internal section links
                if (!this.classList.contains('logout')) {
                    e.preventDefault();
                    const targetId = this.getAttribute('href').substring(1);
                    updateActiveNav(this);
                    showSection(targetId);
                }
                // For logout, let the default behavior happen
            });
        });

        // Show the initial section
        showSection('personal-info');
    });
    </script>
    <jsp:include page="footer.jsp" />
</body>
</html>