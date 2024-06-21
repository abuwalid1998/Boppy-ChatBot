function sendMessage() {
    var userInput = document.getElementById("userInput");
    var message = userInput.value;
    if (message.trim() === "") return;

    var chatBox = document.getElementById("chatBox");
    var newMessage = document.createElement("div");
    newMessage.classList.add("message", "sent");

    var messageContent = document.createElement("div");
    messageContent.classList.add("message-content");
    messageContent.innerHTML = `<p>${message}</p>`;

    var avatar = document.createElement("img");
    avatar.classList.add("avatar");
    avatar.src = "imgs/7309681.jpg"; // Replace with actual avatar URL
    avatar.alt = "Avatar";

    newMessage.appendChild(messageContent);
    newMessage.appendChild(avatar);

    chatBox.appendChild(newMessage);

    userInput.value = "";
    chatBox.scrollTop = chatBox.scrollHeight; // Scroll to bottom

    // Add animation class after a short delay for smooth animation
    setTimeout(function() {
        newMessage.classList.add("animation");
    }, 50); // Adjust timing as needed
}
