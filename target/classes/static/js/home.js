const userId = window.location.pathname.split('/').pop();
const apiUrl = 'http://localhost:8080'; // Replace with your backend base URL

document.addEventListener("DOMContentLoaded", () => {
    loadFollowing();
    loadTopics();
    setupSearch();
});

// Load the list of users the current user is following
async function loadFollowing() {
    try {
        const response = await fetch(`${apiUrl}/getFollowing/${userId}`);
        const following = await response.json();
        const followingList = document.getElementById("followingList");
        followingList.innerHTML = "";
        following.forEach(user => {
            const li = document.createElement("li");
            li.textContent = user.username;
            followingList.appendChild(li);
        });
    } catch (error) {
        console.error("Error loading following list:", error);
    }
}

// Load the list of topics
async function loadTopics() {
    try {
        const response = await fetch(`${apiUrl}/topics/${userId}`);
        const topics = await response.json();
        const topicList = document.getElementById("topicList");
        topicList.innerHTML = "";

        topics.forEach(topic => {
            const div = document.createElement("div");
            div.className = "topic";
            div.setAttribute('data-id', topic.id); // Add the topic ID for easier reference

            const title = document.createElement("h3");
            title.textContent = topic.title;

            const content = document.createElement("p");
            content.textContent = topic.content;

            const viewCommentsBtn = document.createElement("button");
            viewCommentsBtn.className = "btn btn-secondary";
            viewCommentsBtn.textContent = "🗨️ View Comments";
            viewCommentsBtn.onclick = async () => toggleComments(topic.id, div);

            const addCommentBtn = document.createElement("button");
            addCommentBtn.className = "btn btn-primary";
            addCommentBtn.textContent = "➕ Add Comment";
            addCommentBtn.onclick = () => showAddCommentPopup(topic.id);

            div.appendChild(title);
            div.appendChild(content);
            div.appendChild(viewCommentsBtn);
            div.appendChild(addCommentBtn);

            topicList.appendChild(div);
        });
    } catch (error) {
        console.error("Error loading topics:", error);
    }
}

// Toggle the comments visibility
async function toggleComments(topicId, topicDiv) {
    const existingComments = topicDiv.querySelector(".comments");
    if (existingComments) {
        topicDiv.removeChild(existingComments);
        return;
    }

    try {
        const response = await fetch(`${apiUrl}/comments/${topicId}`);
        const comments = await response.json();
        const commentsDiv = document.createElement("div");
        commentsDiv.className = "comments";

        comments.forEach(comment => {
            const p = document.createElement("p");
            p.textContent = comment.text;
            p.className = "comment"; // Apply the comment styling from CSS
            commentsDiv.appendChild(p);
        });

        topicDiv.appendChild(commentsDiv);
    } catch (error) {
        console.error("Error loading comments:", error);
    }
}

// Show the popup for adding a comment
function showAddCommentPopup(topicId) {
    // Get the popup container and input
    const popup = document.getElementById("commentPopup");
    const commentText = document.getElementById("commentText");

    // Clear any previous input
    commentText.value = "";

    // Show the popup
    popup.style.display = "block";

    // Handle the submit button
    const submitButton = document.getElementById("submitComment");
    submitButton.onclick = async () => {
        const commentContent = commentText.value;
        if (!commentContent.trim()) {
            alert("Please enter a comment.");
            return;
        }

        const newComment = { text: commentContent };
        try {
            const response = await fetch(`${apiUrl}/addComment/${userId}/${topicId}`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(newComment),
            });
            const addedComment = await response.json();
            alert("Comment added!");
            popup.style.display = "none"; // Close the popup after posting comment
            toggleComments(topicId, document.querySelector(`.topic[data-id="${topicId}"]`));
        } catch (error) {
            console.error("Error adding comment:", error);
        }
    };

    // Handle the cancel button
    const cancelButton = document.getElementById("cancelPopup");
    cancelButton.onclick = () => {
        popup.style.display = "none"; // Close the popup when Cancel is clicked
    };
}

// Search users based on input
function setupSearch() {
    const searchInput = document.getElementById("searchInput");
    const searchResults = document.getElementById("searchResults");

    searchInput.addEventListener("input", async () => {
        const query = searchInput.value;
        if (query.length === 0) {
            searchResults.innerHTML = "";
            return;
        }

        try {
            const response = await fetch(`${apiUrl}/getByStart`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: query,
            });
            const users = await response.json();
            searchResults.innerHTML = "";
            users.forEach(user => {
                const li = document.createElement("li");
                li.textContent = user.username;

                const followBtn = document.createElement("button");
                followBtn.className = "btn btn-primary";
                followBtn.textContent = "Follow";
                followBtn.onclick = () => followUser(user.username);

                li.appendChild(followBtn);
                searchResults.appendChild(li);
            });
        } catch (error) {
            console.error("Error during search:", error);
        }
    });
}

// Follow a user
async function followUser(toFollowId) {
    try {
        await fetch(`${apiUrl}/follow/${userId}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username: toFollowId }),
        });
        alert("Followed successfully!");
        loadFollowing(); // Refresh the following list
    } catch (error) {
        console.error("Error following user:", error);
    }
}
