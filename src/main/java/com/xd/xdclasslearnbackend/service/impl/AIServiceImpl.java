package com.xd.xdclasslearnbackend.service.impl;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.xd.xdclasslearnbackend.exception.BusinessException;
import com.xd.xdclasslearnbackend.service.AIService;
import io.reactivex.Flowable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Arrays;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * AI服务实现类
 * 实现AI流式交互的核心功能
 *
 * @author 老帆
 */
@Service
public class AIServiceImpl implements AIService {

    // 从配置文件中读取DashScope API密钥
    @Value("${dashscope.api.key}")
    private String apiKey;

    // DashScope Generation实例
    private final Generation generation = new Generation();


    @Override
    public SseEmitter askAIStream(String question) throws BusinessException {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        
        // 校验问题是否为空，避免传给AI导致报错
        if (question == null || question.trim().isEmpty()) {
            try {
                emitter.send(SseEmitter.event().data("Error: 问题不能为空"));
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
            return emitter;
        }

        AtomicBoolean isFirstPrint = new AtomicBoolean(true);

        // 异步执行AI调用，避免阻塞主线程
        new Thread(() -> {
            try {
                // 构建用户消息
                Message userMsg = Message.builder().role(Role.USER.getValue()).content(question).build();

                // 构建生成参数
                GenerationParam param = buildGenerationParam(userMsg);

                // 调用流式接口
                Flowable<GenerationResult> result = generation.streamCall(param);

                // 处理流式响应
                result.blockingForEach(message -> handleGenerationResult(message, emitter, isFirstPrint));
                emitter.complete();
            } catch (NoApiKeyException e) {
                try {
                    emitter.send(SseEmitter.event().data("Error: API密钥无效，请联系管理员配置正确的API密钥。"));
                    emitter.completeWithError(e);
                } catch (Exception sendException) {
                    // 忽略发送异常
                }
            } catch (ApiException e) {
                try {
                    // 检查是否为账户欠费错误
                    String errorMessage = e.getMessage();
                    if (errorMessage != null && (errorMessage.contains("Arrearage") || errorMessage.contains("Access denied") || errorMessage.contains("account is in good standing"))) {
                        emitter.send(SseEmitter.event().data("Error: 账户欠费，请联系管理员充值后再使用AI助手功能。"));
                    } else {
                        emitter.send(SseEmitter.event().data("Error: " + errorMessage));
                    }
                    emitter.completeWithError(e);
                } catch (Exception sendException) {
                    // 忽略发送异常
                }
            } catch (InputRequiredException e) {
                try {
                    emitter.send(SseEmitter.event().data("Error: 输入参数缺失或无效"));
                    emitter.completeWithError(e);
                } catch (Exception sendException) {
                    // 忽略发送异常
                }
            } catch (Exception e) {
                try {
                    emitter.send(SseEmitter.event().data("Error: " + e.getMessage()));
                    emitter.completeWithError(e);
                } catch (Exception sendException) {
                    // 忽略发送异常
                }
            }
        }).start();

        return emitter;
    }

    /**
     * 构建生成参数
     * @param userMsg 用户消息
     * @return 生成参数
     */
    private GenerationParam buildGenerationParam(Message userMsg) {
        return GenerationParam.builder()
                .apiKey(apiKey)
                .model("deepseek-v3.2")
                .enableThinking(true)
                .incrementalOutput(true)
                .resultFormat("message")
                .messages(Arrays.asList(userMsg))
                .build();
    }

    /**
     * 处理生成结果
     * @param message 生成结果
     * @param emitter SSE发射器
     * @param isFirstPrint 是否首次打印标识
     */
    private void handleGenerationResult(GenerationResult message, SseEmitter emitter, AtomicBoolean isFirstPrint) {
        try {
            String reasoning = message.getOutput().getChoices().get(0).getMessage().getReasoningContent();
            String content = message.getOutput().getChoices().get(0).getMessage().getContent();

            if (reasoning != null && !reasoning.isEmpty()) {
                if (isFirstPrint.get()) {
                    emitter.send(SseEmitter.event().data("\n====================思考过程====================\n"));
                    isFirstPrint.set(false);
                }

                emitter.send(SseEmitter.event().data(reasoning));
            }

            if (content != null && !content.isEmpty()) {
                if (!isFirstPrint.get()) {
                    emitter.send(SseEmitter.event().data("\n====================完整回复====================\n"));
                    isFirstPrint.set(true);
                }

                emitter.send(SseEmitter.event().data(content));
            }
        } catch (Exception e) {
            // 忽略处理异常
        }
    }
}