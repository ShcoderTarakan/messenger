import { navigate } from "../navigation.js";
import { sendEvent, addEventListener } from "../socket.js";
import { state } from "../state.js";
import { formatDate, scrollToBottom } from "../utils.js";

const dialogsListNode = document.getElementById("global-dialogs-list");
const messagesNode = document.getElementById("global-messages");
const sendForm = document.getElementById("global-send-form");

const createMessage = (message) => {
  const node = document.createElement("div");
  const userName = state.users.get(message.userId)?.name;
  const date = formatDate(message.createdAt);

  node.className = "message";
  node.innerHTML = `<div class="message__sender">${userName}</div><span class="send-date"> ${date}</span><div class="message__text">${message.text}</div>`;

  if (state.user?.id === message.userId) {
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

  sendEvent({ type: "GLOBAL_MESSAGE_SEND", text });
  sendForm.reset();
};

addEventListener("GLOBAL_MESSAGE", ({ message }) => {
  createMessage(message);
  scrollToBottom(messagesNode);
});

addEventListener("GLOBAL_MESSAGES_LIST", ({ data }) => {
  data.forEach(createMessage);
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

dialogsListNode.addEventListener("click", (event) => {
  const element = event.target.closest("[user-id]");

  const userId = +element?.attributes["user-id"]?.value;

  if (userId) {
    navigate("direct-chat", userId);
  }
});

export class GlobalChat {
  onNavigate() {
    sendForm.reset();
  }
}
