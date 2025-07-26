import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import LoveChat from '../views/LoveChat.vue'
import ManusChat from '../views/ManusChat.vue'

// 根据环境变量设置 API 基础 URL
const API_BASE_URL = process.env.NODE_ENV === 'production'
  ? '/api' // 生产环境使用相对路径，适用于前后端部署在同一域名下
  : 'http://localhost:8123/api' // 开发环境指向本地后端服务

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/write', name: 'WriteChat', component: LoveChat },
  { path: '/manus', name: 'ManusChat', component: ManusChat }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router 