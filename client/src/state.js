import { addEventListener } from "./socket.js";

export const state = {
  user: null,
  users: new Map(),
};

addEventListener("AUTH_SUCCESS", ({ user }) => {
  state.user = user;
});

addEventListener("USER_LIST", ({ users }) => {
  state.users = new Map(users.map((user) => [user.id, user]));
});

addEventListener("NEW_USER", ({ user }) => {
  state.users.set(user.id, user);
});
