package com.cannon.nop.interfaces.config;


import lombok.Data;

@Data
public class ApiResponse {
    private final boolean success;
    private final String message;
}
