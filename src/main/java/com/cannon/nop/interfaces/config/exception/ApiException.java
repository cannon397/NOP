package com.cannon.nop.interfaces.config.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Accessors(chain = true)
public class ApiException extends RuntimeException {
    private String userMessage;
    private String developerMessage;
    private String errorCode;
    private HttpStatus httpStatus;

    @Builder
    public ApiException(ErrorCode errorCode) {
        super(errorCode.getUserMessage());
        this.errorCode = errorCode.getCode();
        this.userMessage = errorCode.getUserMessage();
        this.httpStatus = errorCode.getHttpStatus();
        this.developerMessage = null;
    }
}
