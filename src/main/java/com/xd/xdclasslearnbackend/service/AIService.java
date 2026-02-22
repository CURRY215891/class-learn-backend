package com.xd.xdclasslearnbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


public interface AIService {

    SseEmitter askAIStream(String question);
}
