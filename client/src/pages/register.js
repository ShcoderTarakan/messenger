import { navigate } from "../navigation.js";
import { addEventListener, sendEvent } from "../socket.js";

const registerForm = document.getElementById("register-form");

registerForm.onsubmit = (event) => {
  event.preventDefault();
  const data = new FormData(registerForm);
  const login = data.get("login");
  const name = data.get("name");

  sendEvent({ type: "REGISTER", login, name });
};

document.getElementById("go-back-to-login").onclick = (event) => {
  event.preventDefault();
  navigate("login");
};

addEventListener("REGISTER_ERROR", ({ message }) => {
  alert(`Error: ${message}`);
});

export class RegisterPage {
  onNavigate() {
    registerForm.reset();
  }
}
