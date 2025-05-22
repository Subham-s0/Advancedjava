<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NestAway - About Us</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/about.css">
</head>
<body>
    <header>
       <jsp:include page="header.jsp" />
    </header>
    
    <div class="container">
        <h1 class="page-title">About Us</h1>
        
        <section class="section">
            <h2 class="section-title">Our Story</h2>
            <p class="mission-statement">
                NestAway was founded with a simple mission: to provide exceptional accommodation experiences 
                that feel like home. What started as a small team with a big dream has grown into a trusted 
                name in hospitality, connecting travelers with comfortable, affordable stays in prime locations.
            </p>
            <p class="mission-statement">
                We believe that where you stay should enhance your travel experience, not just be a place to sleep. 
                That's why we carefully select and manage each property to ensure our guests enjoy the perfect 
                blend of comfort, convenience, and local charm.
            </p>
        </section>
        
        <section class="section">
            <h2 class="section-title">Meet Our Team</h2>
            <p class="mission-statement">
                Our dedicated team of hospitality professionals works tirelessly to ensure every NestAway 
                experience exceeds expectations. Get to know the people behind your perfect stay:
            </p>
            
            <div class="team">
                <div class="team-member">
                    <img src="https://randomuser.me/api/portraits/men/32.jpg" alt="Subham Shahi" class="member-image">
                    <h3 class="member-name">SUBHAM SHAHI</h3>
                    <p class="member-role">Co-Founder & CEO</p>
                    <p>The visionary behind NestAway, Subham leads our strategic direction and growth initiatives.</p>
                </div>
                
                <div class="team-member">
                    <img src="https://randomuser.me/api/portraits/women/44.jpg" alt="Aayesha Baidhya" class="member-image">
                    <h3 class="member-name">AAYESHA BAIDHYA</h3>
                    <p class="member-role">Chief Operations Officer</p>
                    <p>Aayesha ensures seamless operations across all our properties and guest experiences.</p>
                </div>
                
                <div class="team-member">
                    <img src="https://randomuser.me/api/portraits/men/75.jpg" alt="Nabin Singh Bhandari" class="member-image">
                    <h3 class="member-name">NABIN SINGH BHANDARI</h3>
                    <p class="member-role">Technology Director</p>
                    <p>Nabin drives our digital transformation and maintains our booking platforms.</p>
                </div>
                
                <div class="team-member">
                    <img src="https://randomuser.me/api/portraits/women/63.jpg" alt="Megha Aryal" class="member-image">
                    <h3 class="member-name">MEGHA ARYAL</h3>
                    <p class="member-role">Guest Experience Manager</p>
                    <p>Megha and her team ensure every guest receives exceptional service throughout their stay.</p>
                </div>
                
                <div class="team-member">
                    <img src="https://randomuser.me/api/portraits/men/81.jpg" alt="Preju Kayastha" class="member-image">
                    <h3 class="member-name">PREJU KAYASTHA</h3>
                    <p class="member-role">Marketing Director</p>
                    <p>Preju leads our branding and marketing efforts to connect with travelers worldwide.</p>
                </div>
            </div>
        </section>
        
        <section class="section">
            <h2 class="section-title">Our Values</h2>
            <div class="values-list">
                <div class="value-item">
                    <h3 class="value-title">Guest First</h3>
                    <p>Every decision we make is guided by what's best for our guests' experience.</p>
                </div>
                
                <div class="value-item">
                    <h3 class="value-title">Quality Assurance</h3>
                    <p>We maintain rigorous standards to ensure consistent quality across all properties.</p>
                </div>
                
                <div class="value-item">
                    <h3 class="value-title">Local Expertise</h3>
                    <p>We provide authentic local experiences, not just accommodations.</p>
                </div>
                
                <div class="value-item">
                    <h3 class="value-title">Innovation</h3>
                    <p>We continuously improve our services through technology and creativity.</p>
                </div>
                
                <div class="value-item">
                    <h3 class="value-title">Sustainability</h3>
                    <p>We're committed to environmentally responsible hospitality practices.</p>
                </div>
                
                <div class="value-item">
                    <h3 class="value-title">Community</h3>
                    <p>We build relationships that benefit guests, property owners, and local communities.</p>
                </div>
            </div>
        </section>
    </div>
    
<jsp:include page="footer.jsp" />
            
    <script>
    document.addEventListener('DOMContentLoaded', function() {
        // Add hover effects to team members
        const teamMembers = document.querySelectorAll('.team-member');
        
        teamMembers.forEach(member => {
            member.addEventListener('mouseenter', function() {
                this.style.transform = 'translateY(-5px)';
                this.style.boxShadow = '0 5px 15px rgba(0,0,0,0.1)';
            });
            
            member.addEventListener('mouseleave', function() {
                this.style.transform = '';
                this.style.boxShadow = '';
            });
        });
        
        // Smooth scrolling for anchor links
        document.querySelectorAll('a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', function(e) {
                e.preventDefault();
                
                const targetId = this.getAttribute('href');
                if (targetId === '#') return;
                
                const targetElement = document.querySelector(targetId);
                if (targetElement) {
                    targetElement.scrollIntoView({
                        behavior: 'smooth'
                    });
                }
            });
        });
    });</script>
</body>
</html>