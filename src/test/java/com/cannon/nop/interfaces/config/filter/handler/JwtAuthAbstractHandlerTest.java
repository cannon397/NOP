package com.cannon.nop.interfaces.config.filter.handler;

import com.cannon.nop.interfaces.auth.JwtProvider;
import com.cannon.nop.interfaces.config.filter.handler.JwtAuthAbstractHandler.EntryPointHandler;
import com.cannon.nop.interfaces.config.filter.handler.JwtAuthAbstractHandler.JwtValidationHandler;
import com.cannon.nop.interfaces.config.filter.handler.JwtAuthAbstractHandler.UrlValidationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.UUID;

class JwtAuthAbstractHandlerTest {

    JwtAuthParser jwtAuthParser;
    static String AUTHORIZATION_HEADER = "Authorization";
    static String SECRET_KEY = "DCuWefoPNhd0rHOAuTtaqJlkUIqjW5W9";
    MockHttpServletRequest request;
    String jwtToken;
    EntryPointHandler EntryPointHandler = new EntryPointHandler();

    @BeforeEach
    void setUp() {
        JwtProvider jwtProvider = new JwtProvider(SECRET_KEY);
        String testEventUUID = UUID.randomUUID().toString();
        jwtToken = jwtProvider.generateToken(testEventUUID);
        request = new MockHttpServletRequest();
        request.setRequestURI("/api/nop/v1/organizer/" + testEventUUID);
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + jwtToken);
        jwtAuthParser = new JwtAuthParser();
        jwtAuthParser.setJwtProvider(jwtProvider);
        jwtAuthParser.setRequest(request);
    }
    @Test
    void testEntryPointHandler(){
        EntryPointHandler.process(jwtAuthParser);
    }

    @Test
    void testUrlValidationHandler() {
        UrlValidationHandler urlValidationHandler = new UrlValidationHandler();
        EntryPointHandler.setNextHandler(urlValidationHandler);
        EntryPointHandler.process(jwtAuthParser);
    }

    @Test
    void testJwtValidationHandler() {
        JwtValidationHandler jwtValidationHandler = new JwtValidationHandler();
        EntryPointHandler.setNextHandler(jwtValidationHandler);
        EntryPointHandler.process(jwtAuthParser);
    }
}