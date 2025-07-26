<template>
  <div class="chat-room">
    <h2>{{ title }}</h2>
    <div class="chat-history">
      <div v-for="(msg, idx) in messages" :key="idx" :class="['msg-row', msg.role]">
        <template v-if="msg.role === 'ai'">
          <img class="avatar" :src="aiAvatar" alt="AI头像" />
          <div class="msg-content ai-content">{{ msg.content }}</div>
        </template>
        <template v-else>
          <div class="msg-content user-content">{{ msg.content }}</div>
        </template>
      </div>
    </div>
    <div class="chat-input">
      <input v-model="input" @keyup.enter="send" placeholder="请输入内容..." />
      <button @click="send">发送</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { v4 as uuidv4 } from 'uuid'
import { chatWithLoveSSE, chatWithManusSSE } from '../utils/api'

const props = defineProps({
  title: String,
  apiType: String
})

const input = ref('')
const messages = ref([])
const chatId = ref('')

const aiAvatar = computed(() => {
  if (props.apiType === 'love') {
    // 灯泡灵感SVG
    return 'data:image/svg+xml;utf8,<svg width="36" height="36" viewBox="0 0 64 64" xmlns="http://www.w3.org/2000/svg"><circle cx="32" cy="32" r="32" fill="%23e6f0ff"/><ellipse cx="32" cy="32" rx="12" ry="14" fill="%23409eff"/><rect x="28" y="44" width="8" height="6" rx="2" fill="%23b2e0ff"/><rect x="30" y="50" width="4" height="4" rx="1" fill="%237ff6ff"/></svg>'
  } else {
    // 机器人SVG
    return 'data:image/svg+xml;utf8,<svg width="36" height="36" viewBox="0 0 64 64" xmlns="http://www.w3.org/2000/svg"><circle cx="32" cy="32" r="32" fill="%23e6f7ff"/><ellipse cx="32" cy="44" rx="14" ry="8" fill="%23b2e0ff"/><circle cx="24" cy="28" r="6" fill="%23fff"/><circle cx="40" cy="28" r="6" fill="%23fff"/><circle cx="24" cy="28" r="2" fill="%23409eff"/><circle cx="40" cy="28" r="2" fill="%23409eff"/><rect x="28" y="36" width="8" height="4" rx="2" fill="%23409eff"/></svg>'
  }
})

onMounted(() => {
  if (props.apiType === 'love') {
    chatId.value = uuidv4()
  }
})

function send() {
  if (!input.value.trim()) return
  const userMsg = { role: 'user', content: input.value }
  messages.value.push(userMsg)
  const currentInput = input.value
  input.value = ''

  if (props.apiType === 'love') {
    chatWithLoveSSE(currentInput, chatId.value, (chunk) => {
      handleAIMessage(chunk)
    })
  } else {
    chatWithManusSSE(currentInput, (chunk) => {
      handleAIMessage(chunk)
    })
  }
}

function handleAIMessage(chunk) {
  if (props.apiType === 'manus') {
    // Step 分步显示，每步自动加一个换行
    const stepRegex = /Step \d+:/g
    const steps = chunk.split(stepRegex).filter(Boolean)
    const matches = chunk.match(stepRegex) || []
    steps.forEach((step, idx) => {
      const prefix = matches[idx] ? matches[idx] + ' ' : ''
      messages.value.push({ role: 'ai', content: (prefix + step.trim() + '\n').replace(/\n+$/, '\n') })
    })
  } else {
    // AI 回复合并到最后一条 AI 消息，去除多余换行
    if (messages.value.length && messages.value[messages.value.length - 1].role === 'ai') {
      messages.value[messages.value.length - 1].content += chunk.replace(/\n+$/, '')
    } else {
      messages.value.push({ role: 'ai', content: chunk.replace(/\n+$/, '') })
    }
  }
}
</script>

<style scoped>
.chat-room {
  max-width: 700px;
  margin: 40px auto;
  border: 1px solid #eee;
  border-radius: 16px;
  padding: 24px;
  background: #f6f8fa;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  height: 80vh;
  min-height: 500px;
  display: flex;
  flex-direction: column;
}
.chat-history {
  flex: 1 1 auto;
  margin-bottom: 16px;
  display: flex;
  flex-direction: column;
  gap: 12px;
  overflow-y: auto;
}
.msg-row {
  display: flex;
  align-items: flex-start;
  gap: 10px;
}
.msg-row.user {
  flex-direction: row-reverse;
}
.avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: #e6f7ff;
  object-fit: cover;
  margin-top: 2px;
}
.msg-content {
  max-width: 70vw;
  word-break: break-all;
  padding: 10px 16px;
  border-radius: 16px;
  margin: 2px 0;
  font-size: 1rem;
  line-height: 1.7;
  box-sizing: border-box;
}
.ai-content {
  background: #f0f0f0;
  color: #222;
  text-align: left;
}
.user-content {
  background: #409eff;
  color: #fff;
  text-align: right;
}
.chat-input {
  display: flex;
  gap: 8px;
  margin-top: 12px;
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  padding: 12px 16px;
  position: sticky;
  bottom: 0;
  z-index: 2;
}
input {
  flex: 1;
  padding: 10px;
  border-radius: 8px;
  border: 1px solid #ccc;
  font-size: 1rem;
}
button {
  padding: 10px 20px;
  border-radius: 8px;
  border: none;
  background: #409eff;
  color: #fff;
  cursor: pointer;
  font-size: 1rem;
}
button:hover {
  background: #66b1ff;
}
@media (max-width: 900px) {
  .chat-room {
    max-width: 98vw;
    padding: 8px;
  }
  .msg-content {
    max-width: 90vw;
  }
}
@media (max-width: 600px) {
  .chat-room {
    max-width: 100vw;
    padding: 2vw;
    font-size: 0.95rem;
  }
  .msg-content {
    font-size: 0.95rem;
    padding: 8px 10px;
  }
  .avatar {
    width: 28px;
    height: 28px;
  }
}
</style> 