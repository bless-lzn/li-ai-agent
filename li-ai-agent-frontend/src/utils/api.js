import { sseRequest } from './sse'

const BASE_URL = process.env.NODE_ENV === 'production'
  ? '/api' // 生产环境，Nginx 代理
  : 'http://localhost:8123/api'; // 开发环境，直连后端

export { BASE_URL };

export function chatWithLoveSSE(message, chatId, onMessage) {
  sseRequest(`${BASE_URL}/ai/love_app/chat/sse?message=${encodeURIComponent(message)}&chatId=${chatId}`, onMessage)
}

export function chatWithManusSSE(message, onMessage) {
  sseRequest(`${BASE_URL}/ai/manus/chat?message=${encodeURIComponent(message)}`, onMessage)
} 