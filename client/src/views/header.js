import { navigate } from "../navigation.js";
import { addEventListener, sendEvent } from "../socket.js";

const usernameNode = document.querySelector("header .username");

addEventListener("AUTH_SUCCESS", ({ user }) => {
  usernameNode.innerText = user.name;
});

addEventListener("LOG_OUT_SUCCESS", ({ user }) => {
  usernameNode.innerText = "";
});

document.querySelector("header button").addEventListener("click", () => {
  sendEvent({ type: "LOG_OUT" });
  navigate("login");
});
