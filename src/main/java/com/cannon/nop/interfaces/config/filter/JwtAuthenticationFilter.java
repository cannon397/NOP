package com.cannon.nop.interfaces.config.filter;

import com.cannon.nop.interfaces.config.exception.ApiException;
import com.cannon.nop.interfaces.config.exception.ErrorCode;
import com.cannon.nop.interfaces.auth.JwtProvider;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final Pattern uuidPattern = Pattern.compile("/api/nop/v1/organizer/([a-fA-F0-9\\-]{36})");

    private final HandlerExceptionResolver handlerExceptionResolver;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String requestURL = request.getRequestURI();
            Matcher matcher = uuidPattern.matcher(requestURL);

            if (matcher.find()) {
                String urlUUID = matcher.group(1);
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
