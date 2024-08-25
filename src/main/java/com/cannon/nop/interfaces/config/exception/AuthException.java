package com.cannon.nop.interfaces.config.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends ApiException {

    public AuthException(String userMessage) {
        super(userMessage, HttpStatus.UNAUTHORIZED);
    }
}
