import { navigate } from "../navigation.js";
import { sendEvent, addEventListener } from "../socket.js";
import { state } from "../state.js";
import { formatDate, scrollToBottom } from "../utils.js";

const dialogsListNode = document.getElementById("direct-dialogs-list");
const messagesNode = document.getElementById("direct-messages");
const sendForm = document.getElementById("direct-send-form");
const titleNode = document.getElementById("direct-chat-title");

let selectedUserId = -1;

const createMessage = (message) => {
  const node = document.createElement("div");
  const userName = state.users.get(message.senderId)?.name;
  const date = formatDate(message.createdAt);

  node.className = "message";
  node.innerHTML = `<div class="message__sender">${userName}</div><span class="send-date"> ${date}</span><div class="message__text">${message.text}</div>`;

  if (state.user?.id === message.senderId) {
    node.classList.add("message_me");
  }

  messagesNode.appendChild(node);
};

const createUser = (user) => {
  const node = document.createElement("div");

  node.className = "channel";
  node.setAttribute("user-id", user.id);
  node.innerHTML = `<div class="channel-name">${user.name}</div>`;

  dialogsListNode.prepend(node);
};

sendForm.onsubmit = (event) => {
  event.preventDefault();
  const text = new FormData(event.target).get("text");

  sendEvent({ type: "DIRECT_MESSAGE_SEND", userId: selectedUserId, text });
  sendForm.reset();
};

addEventListener("DIRECT_MESSAGE", ({ message }) => {
  if (message.senderId === selectedUserId || message.recipientId === selectedUserId) {
    createMessage(message);
    scrollToBottom(messagesNode);
  }
});

addEventListener("DIRECT_MESSAGES_LIST", ({ messages }) => {
  messages.forEach(createMessage);
  scrollToBottom(messagesNode);
});

addEventListener("LOG_OUT_SUCCESS", () => {
  messagesNode.innerText = "";
});

addEventListener("USER_LIST", ({ users }) => {
  dialogsListNode.innerText = "";
  users.forEach((user) => {
    if (user.id !== state.user?.id) {
      createUser(user);
    }
  });
});

addEventListener("NEW_USER", ({ user }) => {
  if (user.id !== state.user?.id) {
    createUser(user);
  }
});

document.getElementById("go-back-to-global-chat").onclick = () => {
  navigate("global-chat");
};

dialogsListNode.addEventListener("click", (event) => {
  const element = event.target.closest("[user-id]");

  const userId = +element?.attributes["user-id"]?.value;

  if (userId) {
    navigate("direct-chat", userId);
  }
});

export class DirectChat {
  onNavigate(userId) {
    messagesNode.innerText = "";

    selectedUserId = userId;
    sendForm.reset();
    sendEvent({ type: "OPEN_DIRECT_CHAT", userId });

    const user = state.users.get(userId);
    if (user) {
      titleNode.innerText = user.name;
    }

    const selected = dialogsListNode.querySelector(".selected");
    if (selected) {
      selected.classList.remove("selected");
    }

    const toSelect = dialogsListNode.querySelector(`[user-id="${userId}"]`);
    if (toSelect) {
      toSelect.classList.add("selected");
    }
  }
}
