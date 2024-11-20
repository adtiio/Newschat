const formTitle = document.getElementById("form-title");
    const toggleForm = document.getElementById("toggle-form");
    const authForm = document.getElementById("auth-form");
    let isSignup = false;

    // Toggle between login and signup
    toggleForm.addEventListener("click", () => {
      isSignup = !isSignup;
      formTitle.innerText = isSignup ? "Signup" : "Login";
      toggleForm.innerText = isSignup
        ? "Already have an account? Login"
        : "Don't have an account? Signup";
    });

    // Handle form submission
    authForm.addEventListener("submit", async (event) => {
      event.preventDefault();

      const username = document.getElementById("username").value;
      const password = document.getElementById("password").value;
      const endpoint = isSignup ? "/signup" : "/signin";
      const apiUrl = `http://localhost:8080${endpoint}`; // Adjust backend URL as needed

      try {
        const response = await fetch(apiUrl, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ username, password }),
        });

        if (!response.ok) {
          throw new Error("Failed to authenticate. Please try again.");
        }

        const userId = await response.json(); // Assume API returns userId
        window.location.href = `/home/${userId}`; // Redirect to homepage with userId
      } catch (error) {
        alert(error.message);
      }
    });