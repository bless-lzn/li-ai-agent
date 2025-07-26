<template>
  <div class="home-bg">
    <div class="home-header">
      <h1>AI智能助手</h1>
      <div class="subtitle">探索AI的无限可能</div>
    </div>
    <div class="card-row">
      <div class="app-card love">
        <div class="icon-wrap">
          <svg width="48" height="48" viewBox="0 0 64 64">
            <circle cx="32" cy="32" r="32" fill="#e6f0ff"/>
            <ellipse cx="32" cy="32" rx="12" ry="14" fill="#409eff"/>
            <rect x="28" y="44" width="8" height="6" rx="2" fill="#b2e0ff"/>
            <rect x="30" y="50" width="4" height="4" rx="1" fill="#7ff6ff"/>
          </svg>
        </div>
        <div class="app-title">AI自媒体文案助手</div>
        <div class="app-desc">智能助手顾问，帮你写生成多种文案</div>
        <router-link to="/write"><button class="try-btn love-btn">立即体验 →</button></router-link>
      </div>
      <div class="app-card manus">
        <div class="icon-wrap">
          <svg width="64" height="64" viewBox="0 0 64 64"><circle cx="32" cy="32" r="32" fill="#e6f7ff"/><g><ellipse cx="32" cy="44" rx="14" ry="8" fill="#b2e0ff"/><circle cx="24" cy="28" r="6" fill="#fff"/><circle cx="40" cy="28" r="6" fill="#fff"/><circle cx="24" cy="28" r="2" fill="#409eff"/><circle cx="40" cy="28" r="2" fill="#409eff"/><rect x="28" y="36" width="8" height="4" rx="2" fill="#409eff"/></g></svg>
        </div>
        <div class="app-title">AI工具智能体</div>
        <div class="app-desc">全能型AI助手，调用工具解决多种问题</div>
        <router-link to="/manus"><button class="try-btn manus-btn">立即体验 →</button></router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
</script>

<style scoped>
html, body {
  height: 100%;
  margin: 0;
  padding: 0;
  background: linear-gradient(135deg, #232946 0%, #394867 100%); /* 与页面主色一致 */
}
#app {
  min-height: 100vh;
}
.home-bg {
  min-height: 100vh;
  background: linear-gradient(135deg, #232946 0%, #394867 100%);
  padding: 0;
}
.home-header {
  text-align: center;
  padding-top: 60px;
  color: #7ff6ff;
  text-shadow: 0 2px 16px #1a1a2e44;
}
.home-header h1 {
  font-size: 2.8rem;
  font-weight: bold;
  margin-bottom: 10px;
  letter-spacing: 2px;
}
.subtitle {
  font-size: 1.2rem;
  color: #b2e0ff;
  margin-bottom: 40px;
}
.card-row {
  display: flex;
  justify-content: center;
  gap: 48px;
  margin-top: 40px;
  flex-wrap: wrap;
}
.app-card {
  background: rgba(30,34,54,0.95);
  border-radius: 24px;
  box-shadow: 0 4px 32px #0002;
  padding: 36px 32px 32px 32px;
  width: 320px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  transition: transform 0.2s, box-shadow 0.2s;
}
.app-card:hover {
  transform: translateY(-8px) scale(1.03);
  box-shadow: 0 8px 40px #0004;
}
.icon-wrap {
  margin-bottom: 18px;
}
.app-title {
  font-size: 1.3rem;
  font-weight: 600;
  color: #fff;
  margin-bottom: 8px;
}
.app-desc {
  color: #b2e0ff;
  font-size: 1rem;
  margin-bottom: 28px;
  text-align: center;
  flex: 1 1 auto;
  width: 100%;
  display: flex;
  align-items: flex-end;
  justify-content: center;
}
.try-btn {
  font-size: 1.1rem;
  padding: 10px 32px;
  border-radius: 24px;
  border: none;
  cursor: pointer;
  font-weight: 600;
  box-shadow: 0 2px 8px #409eff33;
  transition: background 0.2s, color 0.2s;
}
.love-btn {
  background: linear-gradient(90deg, #ff4d6d 60%, #ffb3c6 100%);
  color: #fff;
}
.love-btn:hover {
  background: #ff4d6d;
}
.manus-btn {
  background: linear-gradient(90deg, #409eff 60%, #7ff6ff 100%);
  color: #fff;
}
.manus-btn:hover {
  background: #409eff;
}
@media (max-width: 900px) {
  .card-row {
    flex-direction: column;
    align-items: center;
    gap: 32px;
  }
  .app-card {
    width: 90vw;
    padding: 28px 8vw 24px 8vw;
  }
}
</style> 