import "./views/header.js";
import { navigate } from "./navigation.js";

const savedToken = localStorage.getItem("chat-auth-token");

if (savedToken) {
  navigate("global-chat");
} else {
  navigate("login");
}
