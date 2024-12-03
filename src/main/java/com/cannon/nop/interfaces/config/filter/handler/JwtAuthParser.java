package com.cannon.nop.interfaces.config.filter.handler;

import com.cannon.nop.interfaces.auth.JwtProvider;
import com.cannon.nop.interfaces.config.exception.ApiException;
import com.cannon.nop.interfaces.config.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;


public class JwtAuthParser {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_HEADER = "Bearer ";
    @Setter private HttpServletRequest request;
    @Getter@Setter private JwtProvider jwtProvider;
    @Getter private String requestURI;
    @Getter private String[] parts;
    @Getter private String header;

    public void parse(){
        this.requestURI = request.getRequestURI();
        this.parts = requestURI.split("/");
        this.header = request.getHeader(AUTHORIZATION_HEADER);
    }

    public String parseJwtToken(){
        if(header == null || !header.startsWith(BEARER_HEADER)){
            throw new ApiException(ErrorCode.AUTHORIZATION_HEADER_ERROR);
        }
        String jwtToken = header.substring(BEARER_HEADER.length());
        return jwtToken;
    }


}
