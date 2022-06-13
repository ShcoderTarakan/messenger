export function scrollToBottom(element) {
  element.scrollTo(0, element.scrollHeight);
}

export function formatDate(str) {
  const date = new Date(str);

  return date.toLocaleString("ru");
}
