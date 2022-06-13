const socket = new WebSocket("ws://localhost:3000");

export const sendEvent = (obj) => {
  socket.send(JSON.stringify(obj));
};

const subscribers = {};

export const addEventListener = (event, callback) => {
  if (!subscribers[event]) {
    subscribers[event] = [];
  }

  subscribers[event].push(callback);
};

socket.onmessage = ({ data }) => {
  try {
    const event = JSON.parse(data);

    if (subscribers[event.type]) {
      subscribers[event.type].forEach((callback) => {
        try {
          callback(event);
        } catch (error) {
          console.error(error);
        }
      });
    }
  } catch (error) {
    console.error(error, data);
  }
};
