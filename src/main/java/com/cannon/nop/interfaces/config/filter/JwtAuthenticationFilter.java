package com.cannon.nop.interfaces.config.filter;

import com.cannon.nop.interfaces.auth.JwtProvider;
import com.cannon.nop.interfaces.config.exception.ApiException;
import com.cannon.nop.interfaces.config.filter.handler.JwtAuthHandler;
import com.cannon.nop.interfaces.config.filter.handler.JwtAuthParser;
import com.cannon.nop.interfaces.config.filter.handler.JwtHandlerFactory;
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
    private final UriFilter uriFilter;
    private final JwtHandlerFactory jwtHandlerFactory;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            JwtAuthParser jwtAuthParser = new JwtAuthParser();
            jwtAuthParser.setJwtProvider(jwtProvider);
            jwtAuthParser.setRequest(request);

            String requestURL = request.getRequestURI();
            if (uriFilter.isExcludedUri(requestURL)) {
                filterChain.doFilter(request, response);
                return;
            }

            JwtAuthHandler jwtAuthHandler = jwtHandlerFactory.create();
            jwtAuthHandler.handle(jwtAuthParser);

            filterChain.doFilter(request, response);
        } catch (ApiException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
