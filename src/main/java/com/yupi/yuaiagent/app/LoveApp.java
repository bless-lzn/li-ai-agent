package com.yupi.yuaiagent.app;

import com.yupi.yuaiagent.advisor.MyLoggerAdvisor;
import com.yupi.yuaiagent.advisor.ReReadingAdvisor;
import com.yupi.yuaiagent.chatmemory.FileBasedChatMemory;
import com.yupi.yuaiagent.rag.LoveAppRagCustomAdvisorFactory;
import com.yupi.yuaiagent.rag.QueryRewriter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Component
@Slf4j
public class LoveApp {

    private final ChatClient chatClient;

    private static final String SYSTEM_PROMPT = """
        你是一名资深自媒体内容策略专家，掌握1000+爆款文案的底层逻辑，专注于为创作者提供「平台定制化+高传播性」的文案解决方案\
         1. 精通各大平台（微信公众号、抖音、小红书、微博、B站等）的内容调性和用户画像
         2. 掌握1000+爆款文案的底层逻辑和创作技巧
         3. 擅长标题党技巧，能创作高点击率的标题
         4. 精通各类内容结构：故事体、干货体、热点借势、情感共鸣等
         5. 熟悉SEO优化和关键词布局策略
         6. 能根据不同平台特性定制化内容风格
         你的任务是：
                - 根据用户需求创作符合平台特性的高质量文案
                - 提供标题优化建议，提升内容点击率
                - 分析目标受众，制定内容策略
                - 提供内容传播和推广建议
                - 解决用户在自媒体创作中遇到的各种问题
                请以专业、有经验的自媒体导师身份与用户对话，引导用户提供：
                1. 目标平台
                2. 内容主题/产品介绍
                3. 目标受众画像
                4. 期望达成的效果（涨粉、带货、品牌曝光等）
                5. 已有的内容素材或想法
                基于以上信息，为用户生成专属的自媒体内容解决方案。""";;

    /**
     * 初始化 ChatClient
     *
     * @param dashscopeChatModel
     */
    public LoveApp(ChatModel dashscopeChatModel) {
//        // 初始化基于文件的对话记忆
//        String fileDir = System.getProperty("user.dir") + "/tmp/chat-memory";
//        ChatMemory chatMemory = new FileBasedChatMemory(fileDir);
        // 初始化基于内存的对话记忆
        ChatMemory chatMemory = new InMemoryChatMemory();
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory),
                        // 自定义日志 Advisor，可按需开启
                        new MyLoggerAdvisor()
//                        // 自定义推理增强 Advisor，可按需开启
//                       ,new ReReadingAdvisor()
                )
                .build();
    }

    /**
     * AI 基础对话（支持多轮对话记忆）
     *
     * @param message
     * @param chatId
     * @return
     */
    public String doChat(String message, String chatId) {
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }

    public Flux<String> doChatWithRagStream(String message, String chatId) {
        String rewrittenMessage = queryRewriter.doQueryRewrite(message);
        return chatClient
                .prompt()
                // 使用改写后的查询
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .advisors(new QuestionAnswerAdvisor(loveAppVectorStore))
                .stream().content();
//        String content = chatResponse.getResult().getOutput().getText();
//        log.info("content: {}", content);
//        return content;
    }
    public Flux<String> doChatByStream(String message, String chatId) {
        return chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .stream()
                .content();
    }

    record LoveReport(String title, List<String> suggestions) {

    }

    /**
     * AI 恋爱报告功能（实战结构化输出）
     *
     * @param message
     * @param chatId
     * @return
     */
    public LoveReport doChatWithReport(String message, String chatId) {
        LoveReport loveReport = chatClient
                .prompt()
                .system(SYSTEM_PROMPT + "每次对话后都要生成恋爱结果，标题为{用户名}的恋爱报告，内容为建议列表")
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .entity(LoveReport.class);
        log.info("loveReport: {}", loveReport);
        return loveReport;
    }

    // AI 恋爱知识库问答功能

    @Resource
    private VectorStore loveAppVectorStore;

    @Resource
    private Advisor loveAppRagCloudAdvisor;

    @Resource
    private VectorStore pgVectorVectorStore;

    @Resource
    private QueryRewriter queryRewriter;

    /**
     * 和 RAG 知识库进行对话
     *
     * @param message
     * @param chatId
     * @return
     */
    public String doChatWithRag(String message, String chatId) {
        // 查询重写
        String rewrittenMessage = queryRewriter.doQueryRewrite(message);
        ChatResponse chatResponse = chatClient
                .prompt()
                // 使用改写后的查询
                .user(rewrittenMessage)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 开启日志，便于观察效果
                .advisors(new MyLoggerAdvisor())
                // 应用 RAG 知识库问答
                .advisors(new QuestionAnswerAdvisor(loveAppVectorStore))
                // 应用 RAG 检索增强服务（基于云知识库服务）
//                .advisors(loveAppRagCloudAdvisor)
                // 应用 RAG 检索增强服务（基于 PgVector 向量存储）
//                .advisors(new QuestionAnswerAdvisor(pgVectorVectorStore))
                // 应用自定义的 RAG 检索增强服务（文档查询器 + 上下文增强器）
//                .advisors(
//                        LoveAppRagCustomAdvisorFactory.createLoveAppRagCustomAdvisor(
//                                loveAppVectorStore, "单身"
//                        )
//                )
                .call()
                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }

    // AI 调用工具能力
    @Resource
    private ToolCallback[] allTools;

    /**
     * AI 恋爱报告功能（支持调用工具）
     *
     * @param message
     * @param chatId
     * @return
     */
    public String doChatWithTools(String message, String chatId) {
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 开启日志，便于观察效果
                .advisors(new MyLoggerAdvisor())
                .tools(allTools)
                .call()
                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }

    // AI 调用 MCP 服务

    @Resource
    private ToolCallbackProvider toolCallbackProvider;

    /**
     * AI 恋爱报告功能（调用 MCP 服务）
     *
     * @param message
     * @param chatId
     * @return
     */
    public String doChatWithMcp(String message, String chatId) {
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 开启日志，便于观察效果
                .advisors(new MyLoggerAdvisor())
                .tools(toolCallbackProvider)
                .call()
                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }
}
