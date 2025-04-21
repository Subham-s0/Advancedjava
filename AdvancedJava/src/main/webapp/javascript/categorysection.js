// Dummy data structure that mimics database data

const categoryData = [
    {
        id: 1,
        name: "All",
        icon: "grid",
    },
    {
        id: 2,
        name: "Trending",
        icon: "flame",
    },
    {
        id: 3,
        name: "Rooms",
        icon: "bed",
    },
    {
        id: 4,
        name: "Hotels",
        icon: "building",
    },
    {
        id: 5,
        name: "Apartment",
        icon: "building-2",
    },
	{
	       id: 6,
	       name: "LAkes",
	       icon: "building-2",
	   }
];

// Dummy hotel data that mimics database data
const hotelData = [
    {
        id: 1,
        name: "Premium City View Suite",
        location: "Downtown Manhattan, NY",
        price: 299,
        image: "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80",
        category: "Hotels"
    },
    {
        id: 2,
        name: "Oceanfront Paradise Villa",
        location: "Miami Beach, FL",
        price: 459,
        image: "https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80",
        category: "Trending"
    },
    {
        id: 3,
        name: "Alpine Mountain Retreat",
        location: "Swiss Alps, Switzerland",
        price: 599,
        image: "https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80",
        category: "Rooms"
    },
	{
	    id: 4,
	    name: "Historic French Quarter Mansion",
	    location: "New Orleans, LA",
	    price: 199,
	    image: "https://images.unsplash.com/photo-1596394516093-501ba68a0ba6?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80",
	    category: "Hotels"
	},
	{
	    id: 5,
	    name: "Overwater Bungalow Paradise",
	    location: "Baa Atoll, Maldives",
	    price: 399,
	    image: "https://images.unsplash.com/photo-1591824438708-ce405f16ba3c?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80",
	    category: "Trending"
	},
	{
	    id: 6,
	    name: "Cozy Mountain Cabin Escape",
	    location: "Aspen, CO",
	    price: 349,
	    image: "https://images.unsplash.com/photo-1600585154526-990dced4db0d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80",
	    category: "Rooms"
		
	},
	{
	        id: 7,
	        name: "Oceanfront Paradise Villa",
	        location: "Miami Beach, FL",
	        price: 459,
	        image: "https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80",
	        category: "LAkes"
	    }
];

// Function to render categories
function renderCategories() {
    const categoryBar = document.querySelector('.category-bar');
    if (!categoryBar) {
        console.error('Category bar element not found');
        return;
    }

    categoryBar.innerHTML = ''; // Clear existing content

    categoryData.forEach(category => {
        const button = document.createElement('button');
        button.className = `category-button ${category.active ? 'active' : ''}`;
        button.setAttribute('data-category-id', category.id);
        button.innerHTML = `
            <i data-lucide="${category.icon}"></i>
            <span>${category.name}</span>
        `;
        categoryBar.appendChild(button);
    });

    // Initialize Lucide icons
    lucide.createIcons();

    // Set first category as active by default
    const firstButton = categoryBar.querySelector('.category-button');
    if (firstButton) {
        firstButton.classList.add('active');
        const categoryName = firstButton.querySelector('span').textContent;
        renderHotels(categoryName);
    }
}

// Function to render hotels
function renderHotels(category = 'All') {
    const hotelGrid = document.querySelector('.hotel-grid');
    if (!hotelGrid) {
        console.error('Hotel grid element not found');
        return;
    }

    hotelGrid.innerHTML = ''; // Clear existing content

    const filteredHotels = category === 'All' 
        ? hotelData 
        : hotelData.filter(hotel => hotel.category === category);

    filteredHotels.forEach(hotel => {
        const card = document.createElement('div');
        card.className = 'hotel-card';
        card.innerHTML = `
            <div class="hotel-image">
                <img src="${hotel.image}" alt="${hotel.name}">
                <button class="wishlist-button" data-hotel-id="${hotel.id}">
                    <i data-lucide="heart"></i>
                </button>
            </div>
            <div class="hotel-info">
                <h3>${hotel.name}</h3>
                <p>${hotel.location}</p>
                <div class="hotel-footer">
                    <div class="price">
                        <span class="amount">$${hotel.price}</span>
                        <span class="per-night">/night</span>
						<form action="${window.contextPath}/BookingController" method="post">
						                    <input type="hidden" name="hotelId" value="${hotel.id}">
						                    <button type="submit" class="book-button">Book Now</button>
						                </form>
						            </div>
            </div>
        `;
        hotelGrid.appendChild(card);
    });

    // Initialize Lucide icons
    lucide.createIcons();

    // Add wishlist click handlers
    document.querySelectorAll('.wishlist-button').forEach(button => {
        button.addEventListener('click', () => {
            const icon = button.querySelector('i');
            const isFilled = icon.getAttribute('data-lucide') === 'heart-fill';
            
            if (isFilled) {
                icon.setAttribute('data-lucide', 'heart');
                icon.style.fill = 'none';
            } else {
                icon.setAttribute('data-lucide', 'heart-fill');
                icon.style.fill = 'currentColor';
            }
            
            lucide.createIcons();
            button.classList.toggle('active');
        });
    });
}

// Function to handle category selection
function handleCategorySelection() {
    const categoryButtons = document.querySelectorAll('.category-button');
    
    categoryButtons.forEach(button => {
        button.addEventListener('click', () => {
            // Remove active class from all buttons
            categoryButtons.forEach(btn => btn.classList.remove('active'));
            
            // Add active class to clicked button
            button.classList.add('active');
            
            // Get category name from button text
            const categoryName = button.querySelector('span').textContent;
            
            // Render hotels for selected category
            renderHotels(categoryName);
        });
    });
}

// Initialize when the DOM is fully loaded
document.addEventListener('DOMContentLoaded', () => {
    console.log('Initializing category bar...');
    renderCategories();
    handleCategorySelection();
});

// If using JavaScript for dynamic icon change
document.querySelectorAll('.wishlist-button').forEach(button => {
    button.addEventListener('mouseenter', () => {
        const icon = button.querySelector('i');
        icon.setAttribute('data-lucide', 'heart-fill');
        lucide.createIcons();
    });
    
    button.addEventListener('mouseleave', () => {
        const icon = button.querySelector('i');
        icon.setAttribute('data-lucide', 'heart');
        lucide.createIcons();
    });
});