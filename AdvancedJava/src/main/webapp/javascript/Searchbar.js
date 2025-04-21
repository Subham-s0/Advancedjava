const searchContainer = document.querySelector('.search-container');
    window.addEventListener('scroll', () => {
        const scrollY = window.scrollY;
        
        if (scrollY > 10) {
            searchContainer.classList.add('sticky');
            // Add compact class when scrolling down (unless already expanded)
            if (!searchContainer.classList.contains('expanded')) {
                searchContainer.classList.add('compact');
            }
        } else {
            searchContainer.classList.remove('sticky');
            searchContainer.classList.remove('compact');
            searchContainer.classList.remove('expanded');
        }
    });

    // Handle clicks on search container or icons
    searchContainer.addEventListener('click', (e) => {
        // Check if click is on any search input or icon within the container
        if (e.target.closest('.search-input') || e.target.closest('[data-lucide]')) {
            if (searchContainer.classList.contains('sticky') && 
                searchContainer.classList.contains('compact')) {
                // Remove compact class and add expanded marker
                searchContainer.classList.remove('compact');
                searchContainer.classList.add('expanded');
            }
        }
    });

    // Close expanded search when clicking outside
    document.addEventListener('click', (e) => {
        if (!searchContainer.contains(e.target) && 
            searchContainer.classList.contains('sticky') &&
            searchContainer.classList.contains('expanded')) {
            // Return to compact mode
            searchContainer.classList.add('compact');
            searchContainer.classList.remove('expanded');
        }
    });

    // Initialize date picker - will work as it searches within entire document
    flatpickr("#dateRange", {
        mode: "range",
        minDate: "today",
        dateFormat: "Y-m-d",
        placeholder: "Check-in - Check-out"
    });

    // Location dropdown functionality - updated to work with included content
    document.querySelector('.search-container').addEventListener('click', function(e) {
        // Handle location dropdown toggle
        if (e.target.closest('.location-input')) {
            const dropdown = this.querySelector('.location-dropdown');
            dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
            this.querySelector('.guest-dropdown').style.display = 'none';
        }
        
        // Handle guest dropdown toggle
        if (e.target.closest('.guest-selector')) {
            const dropdown = this.querySelector('.guest-dropdown');
            dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
            this.querySelector('.location-dropdown').style.display = 'none';
        }
        
        // Handle location item selection
        if (e.target.closest('.location-item')) {
            const item = e.target.closest('.location-item');
            this.querySelector('#location').value = item.textContent;
            this.querySelector('.location-dropdown').style.display = 'none';
        }
    });

    // Close dropdowns when clicking outside
    document.addEventListener('click', function(e) {
        const searchContainer = document.querySelector('.search-container');
        if (!e.target.closest('.search-input')) {
            searchContainer.querySelector('.location-dropdown').style.display = 'none';
            searchContainer.querySelector('.guest-dropdown').style.display = 'none';
        }
    });
    