package com.xd.xdclasslearnbackend.controller;

import com.xd.xdclasslearnbackend.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

/**
 * @author 康志阳
 * @date 2026/2/21 16:38
 */
@RestController
@RequestMapping("/api/assistant")
public class AIController {

    @Autowired
    private AIService aiService;

    @PostMapping("/ask/stream")
    public SseEmitter askAIStream(@RequestBody Map<String, String> request){
        String question = request.get("question");
        System.out.println("DEBUG: AIController received question: " + question);
        return aiService.askAIStream(question);
    }
}
