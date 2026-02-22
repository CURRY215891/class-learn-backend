package com.xd.xdclasslearnbackend.exception;

import com.xd.xdclasslearnbackend.commen.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 康志阳
 * @date 2026/2/18 16:49
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public Result<Map<String, Object>> handleBusinessException(BusinessException ex, HttpServletRequest request) {
       log.info("业务异常：{}",ex.getMessage(),ex);
        Map<String, Object> errorInfo = buildErrorInfo(ex.getErrorCode(), ex.getMessage(), request);
        return Result.error(ex.getErrorCode(),"业务异常");
    }

    /**
     * 处理参数校验异常（通过@Valid注解触发）
     * @param ex 参数校验异常
     * @param request HTTP请求
     * @return 错误响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Map<String, Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("参数校验异常: {}", ex.getMessage(), ex);

        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        Map<String, Object> errorInfo = buildErrorInfo(400, errorMessage, request);
        return Result.error(400, "参数校验失败");
    }
    /**
     * 处理参数校验异常（通过@Validated注解触发）
     * @param ex 参数校验异常
     * @param request HTTP请求
     * @return 错误响应
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Map<String, Object>> handleConstraintViolationException(
            ConstraintViolationException ex, HttpServletRequest request) {
        log.error("参数校验异常: {}", ex.getMessage(), ex);

        String errorMessage = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));

        Map<String, Object> errorInfo = buildErrorInfo(400, errorMessage, request);
        return Result.error(400, "参数校验失败");
    }

    /**
     * 处理其他未被捕获的异常
     * @param ex 异常
     * @param request HTTP请求
     * @return 错误响应
     */
    @ExceptionHandler(Exception.class)
    public Result<Map<String, Object>> handleException(Exception ex, HttpServletRequest request) {
        log.error("未处理的异常: {}", ex.getMessage(), ex);

        Map<String, Object> errorInfo = buildErrorInfo(500, "服务器内部错误", request);
        return Result.error(500, "服务器内部错误");
    }

    private Map<String,Object>buildErrorInfo(Integer errorCode,String errorMessage,HttpServletRequest request){
        Map<String,Object> errorInfo = new HashMap<>();
        errorInfo.put("error_code",errorCode);
        errorInfo.put("error_message",errorMessage);
        errorInfo.put("timestamp", LocalDateTime.now().toString());
        errorInfo.put("path",request.getRequestURI());
        return errorInfo;
    }
}
