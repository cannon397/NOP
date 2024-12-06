package com.cannon.nop.interfaces.config.filter;

import com.cannon.nop.interfaces.auth.JwtProvider;
import com.cannon.nop.interfaces.config.exception.ApiException;
import com.cannon.nop.interfaces.config.filter.handler.JwtAuthAbstractHandler;
import com.cannon.nop.interfaces.config.filter.handler.JwtHandlerFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.UUID;

import static org.mockito.Mockito.*;

@Slf4j
class JwtAuthenticationFilterTest {

    static String SECRET_KEY = "DCuWefoPNhd0rHOAuTtaqJlkUIqjW5W9";
    MockHttpServletRequest request;
    MockHttpServletResponse response;
    MockFilterChain filterChain;
    JwtAuthenticationFilter filter;
    JwtHandlerFactory handlerFactory;
    HandlerExceptionResolver exceptionResolver;
    @BeforeEach
    void setUp(){
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = new MockFilterChain();
        // 필터 생성 및 의존성 연결
        JwtProvider jwtProvider = new JwtProvider(SECRET_KEY);
        UriFilter uriFilter = new UriFilter();
        exceptionResolver = mock(HandlerExceptionResolver.class);
        handlerFactory = new JwtHandlerFactory(
                new JwtAuthAbstractHandler.EntryPointHandler(),
                new JwtAuthAbstractHandler.UrlValidationHandler(),
                new JwtAuthAbstractHandler.JwtValidationHandler()
                );
        String evenUrlUUID = UUID.randomUUID().toString();
        String jwtToken = jwtProvider.generateToken(evenUrlUUID);
        request.setRequestURI("/api/nop/v1/organizer/" + evenUrlUUID);
        request.addHeader("Authorization", "Bearer " + jwtToken);
        filter = new JwtAuthenticationFilter(
                jwtProvider,
                exceptionResolver,
                uriFilter,
                handlerFactory
        );
    }
    @Test
    void doFilterInternal() {
        try {
            filter.doFilterInternal(request,response,filterChain);
            verify(exceptionResolver, times(0)).resolveException(eq(request), eq(response), isNull(), any(ApiException.class));
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }
}