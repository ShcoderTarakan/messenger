import { navigate } from "../navigation.js";
import { addEventListener, sendEvent } from "../socket.js";

const loginForm = document.getElementById("loginForm");

loginForm.onsubmit = (event) => {
  event.preventDefault();
  const data = new FormData(loginForm);
  const login = data.get("login");

  sendEvent({ type: "AUTH", login });
};

addEventListener("AUTH_SUCCESS", () => {
  navigate("global-chat");
});

addEventListener("AUTH_ERROR", () => {
  alert("Cannot login");
});

document.getElementById("create-new-user").onclick = (event) => {
  event.preventDefault();
  navigate("register");
};

export class LoginPage {
  onNavigate() {
    loginForm.reset();
  }
}
