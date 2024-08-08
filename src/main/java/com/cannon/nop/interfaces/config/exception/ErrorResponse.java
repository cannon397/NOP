package com.cannon.nop.interfaces.config.exception;

import lombok.Builder;


public class ErrorResponse {
    private String code;
    private String message;

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
