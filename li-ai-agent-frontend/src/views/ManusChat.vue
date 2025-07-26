<template>
  <div class="manus-bg">
    <div class="manus-header">
      <svg width="48" height="48" viewBox="0 0 64 64"><circle cx="32" cy="32" r="32" fill="#e6f7ff"/><g><ellipse cx="32" cy="44" rx="14" ry="8" fill="#b2e0ff"/><circle cx="24" cy="28" r="6" fill="#fff"/><circle cx="40" cy="28" r="6" fill="#fff"/><circle cx="24" cy="28" r="2" fill="#409eff"/><circle cx="40" cy="28" r="2" fill="#409eff"/><rect x="28" y="36" width="8" height="4" rx="2" fill="#409eff"/></g></svg>
      <div class="manus-title">AI超级智能体</div>
      <div class="manus-desc">全能型AI助手，科技感十足，助你解决专业难题</div>
    </div>
    <ChatRoom :title="''" :apiType="'manus'" />
  </div>
</template>

<script setup>
import ChatRoom from '../components/ChatRoom.vue'
</script>

<style scoped>
.manus-bg {
  min-height: 100vh;
  background: linear-gradient(135deg, #232946 0%, #7ff6ff 100%);
  padding: 0;
}
.manus-header {
  text-align: center;
  padding-top: 40px;
  color: #409eff;
}
.manus-title {
  font-size: 2rem;
  font-weight: bold;
  margin: 10px 0 4px 0;
  letter-spacing: 1px;
}
.manus-desc {
  font-size: 1.1rem;
  color: #7ff6ff;
  margin-bottom: 24px;
}
</style> 