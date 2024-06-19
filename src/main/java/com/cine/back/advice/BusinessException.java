package com.cine.back.advice;

public class BusinessException extends RuntimeException {
    
    public BusinessException(String message) {
        super(message);
    }
}
