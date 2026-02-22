package com.xd.xdclasslearnbackend.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 日志切面类
 * 用于记录方法调用的日志信息，包括调用时间、方法名称和参数信息
 * @author 康志阳
 * @date 2026/2/18 16:19
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Before("execution(* com.xd.xdclasslearnbackend.controller..*.*(..)) || " +
            "execution(* com.xd.xdclasslearnbackend.service..*.*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint){
        String currentTime = LocalDateTime.now().toString();
        String className = joinPoint.getSignature().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String argsString = Arrays.toString(args);
        log.info("日志:"+"时间"+currentTime+","+"类名"+className+","+"方法名"+methodName+","+"参数"+argsString);
    }

}
