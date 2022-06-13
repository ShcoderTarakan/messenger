import { DirectChat } from "./pages/direct-chat.js";
import { GlobalChat } from "./pages/global-chat.js";
import { LoginPage } from "./pages/login.js";
import { RegisterPage } from "./pages/register.js";

const pages = {
  "global-chat": new GlobalChat(),
  "direct-chat": new DirectChat(),
  login: new LoginPage(),
  register: new RegisterPage(),
};

export const navigate = (page, params) => {
  document.body.setAttribute("active-page", page);
  pages[page].onNavigate(params);
};
