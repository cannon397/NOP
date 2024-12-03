package com.cannon.nop.interfaces.config.filter.handler;

import com.cannon.nop.interfaces.config.filter.handler.JwtAuthAbstractHandler.JwtValidationHandler;
import com.cannon.nop.interfaces.config.filter.handler.JwtAuthAbstractHandler.UrlValidationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class JwtHandlerFactory {
    private final UrlValidationHandler urlValidationHandler;
    private final JwtValidationHandler jwtValidationHandler;

    public JwtAuthHandler create() {
        urlValidationHandler.setNextHandler(jwtValidationHandler);
        return urlValidationHandler;
    }
}
