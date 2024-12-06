package com.cannon.nop.interfaces.config.filter.handler;

import com.cannon.nop.interfaces.auth.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtAuthParser {
    private HttpServletRequest request;
    private JwtProvider jwtProvider;
    private String[] parts;
    private String header;
    private String jwtToken;
}
