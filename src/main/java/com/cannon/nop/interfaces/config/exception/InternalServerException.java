package com.cannon.nop.interfaces.config.exception;

public class InternalServerException extends RuntimeException{
    public InternalServerException(String message) {
        super(message);
    }
    // 메시지와 원인(cause)를 받는 생성자
    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
