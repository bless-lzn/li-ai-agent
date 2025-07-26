export function sseRequest(url, onMessage) {
  const eventSource = new EventSource(url)
  eventSource.onmessage = (event) => {
    if (event.data) {
      onMessage(event.data)
    }
  }
  eventSource.onerror = () => {
    eventSource.close()
  }
  return eventSource
} 