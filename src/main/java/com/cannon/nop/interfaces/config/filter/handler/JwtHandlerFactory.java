package com.cannon.nop.interfaces.config.filter.handler;

import com.cannon.nop.interfaces.config.filter.handler.JwtAuthAbstractHandler.JwtValidationHandler;
import com.cannon.nop.interfaces.config.filter.handler.JwtAuthAbstractHandler.UrlValidationHandler;
import com.cannon.nop.interfaces.config.filter.handler.JwtAuthAbstractHandler.EntryPointHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class JwtHandlerFactory {
    private final EntryPointHandler  parseHandler;
    private final UrlValidationHandler urlValidationHandler;
    private final JwtValidationHandler jwtValidationHandler;

    public JwtAuthHandler create() {
        parseHandler.setNextHandler(urlValidationHandler);
        urlValidationHandler.setNextHandler(jwtValidationHandler);
        return parseHandler;
    }
}
