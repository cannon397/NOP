package com.cannon.nop.interfaces.config.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiException extends RuntimeException {
    private String userMessage;
    private String developerMessage;
    private String errorCode;
    private HttpStatus httpStatus;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getUserMessage());
        this.errorCode = errorCode.getCode();
        this.userMessage = errorCode.getUserMessage();
        this.httpStatus = errorCode.getHttpStatus();
        this.developerMessage = null;
    }
    public ApiException(ErrorCode errorCode, String developerMessage) {
        super(errorCode.getUserMessage());
        this.errorCode = errorCode.getCode();
        this.userMessage = errorCode.getUserMessage();
        this.httpStatus = errorCode.getHttpStatus();
        this.developerMessage = developerMessage;
    }
}
