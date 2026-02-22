package com.xd.xdclasslearnbackend.exception;

/**
 * @author 康志阳
 * @date 2026/2/18 16:14
 */
public class BusinessException extends RuntimeException {

    private Integer errorCode;

    public BusinessException(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public BusinessException(Integer errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String message) {
        super(message);
        this.errorCode = 500;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
