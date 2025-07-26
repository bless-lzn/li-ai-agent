<template>
  <div class="media-bg">
    <div class="media-header">
      <svg width="56" height="56" viewBox="0 0 64 64">
        <circle cx="32" cy="32" r="32" fill="#e6f0ff"/>
        <ellipse cx="32" cy="32" rx="12" ry="14" fill="#409eff"/>
        <rect x="28" y="44" width="8" height="6" rx="2" fill="#b2e0ff"/>
        <rect x="30" y="50" width="4" height="4" rx="1" fill="#7ff6ff"/>
      </svg>
      <div class="media-title">AI自媒体文案助手</div>
      <div class="media-desc">释放创意灵感，轻松生成高质量自媒体内容</div>
    </div>
    <ChatRoom :title="''" :apiType="'love'" />
  </div>
</template>

<script setup>
import ChatRoom from '../components/ChatRoom.vue'
</script>

<style scoped>
.media-bg {
  min-height: 100vh;
  background: linear-gradient(135deg, #232946 0%, #7ff6ff 100%);
  padding: 0;
}
.media-header {
  text-align: center;
  padding-top: 48px;
  color: #409eff;
}
.media-title {
  font-size: 2.6rem;
  font-weight: bold;
  margin: 18px 0 8px 0;
  letter-spacing: 2px;
}
.media-desc {
  font-size: 1.3rem;
  color: #7ff6ff;
  margin-bottom: 32px;
}
</style> 