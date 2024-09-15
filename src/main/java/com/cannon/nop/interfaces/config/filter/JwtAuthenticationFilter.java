package com.cannon.nop.interfaces.config.filter;

import com.cannon.nop.interfaces.auth.JwtProvider;
import com.cannon.nop.interfaces.config.exception.ApiException;
import com.cannon.nop.interfaces.config.exception.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;


    private final HandlerExceptionResolver handlerExceptionResolver;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String requestURL = request.getRequestURI();
            String[] parts = requestURL.split("/");
            if(requestURL.equals("/api/nop/v1/organizer") || requestURL.equals("/api/nop/v1/organizer/")){
                filterChain.doFilter(request, response);
                return;
            }
            if (parts.length >= 6 && parts[5].length() == 36) {
                String urlUUID = parts[5];
                final String authHeader = request.getHeader("Authorization");
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    String token = authHeader.substring(7);
                    String tokenUUID = jwtProvider.extractEventUrlUUID(token);

                    if (!jwtProvider.isTokenExpired(token) && urlUUID.equals(tokenUUID)) {
                        filterChain.doFilter(request, response);
                    }
                }else{
                    throw new ApiException(ErrorCode.AUTHORIZATION_HEADER_ERROR);
                }
            }else{
                throw new ApiException(ErrorCode.NOT_FOUND_RESOURCE);
            }
        } catch (ApiException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
