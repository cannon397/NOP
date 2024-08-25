package com.cannon.nop.interfaces.config.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends ApiException{
    public ConflictException(String userMessage) {
        super(userMessage, HttpStatus.CONFLICT);
    }
}
