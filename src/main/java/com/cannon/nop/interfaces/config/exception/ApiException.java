package com.cannon.nop.interfaces.config.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiException extends RuntimeException{
    private String userMessage;
    private HttpStatus status;

    public ApiException(String userMessage,HttpStatus status) {
        super(userMessage);
        this.status = status;
        this.userMessage = userMessage;
    }
}
