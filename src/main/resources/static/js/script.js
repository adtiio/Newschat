// Function to format date
function formatDate(dateStr) {
    const date = new Date(dateStr);
    return date.toLocaleDateString() + " " + date.toLocaleTimeString();
}

// Function to dynamically generate cards
function displayNews(news) {
    const newsContainer = document.getElementById("news-container");
    newsContainer.innerHTML = ""; // Clear any existing content

    news.articles.forEach(article => {
        // Create card element
        const card = document.createElement("div");
        card.classList.add("card");

        // Set inner HTML of the card
        card.innerHTML = `
            <img src="${article.urlToImage}" alt="News Image">
            <div class="card-content">
                <h3 class="card-title">${article.title}</h3>
                <p class="card-description">${article.description}</p>
                <div class="card-footer">
                    <span>${formatDate(article.publishedAt)}</span>
                    <a href="${article.url}" target="_blank">Read More</a>
                </div>
            </div>
        `;

        // Append card to the container
        newsContainer.appendChild(card);
    });
}

// Fetch data from backend API
function fetchNews() {
    const apiUrl = "http://localhost:8080/news/data";

    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json(); // Parse the JSON response
        })
        .then(data => {
            displayNews(data); // Pass the fetched data to the display function
        })
        .catch(error => {
            console.error("Error fetching news:", error);
            const newsContainer = document.getElementById("news-container");
            newsContainer.innerHTML = `<p class="error-message">Unable to fetch news. Please try again later.</p>`;
        });
}

// Call fetchNews when the page loads
document.addEventListener("DOMContentLoaded", fetchNews);
