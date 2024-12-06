package com.cannon.nop.interfaces.config.filter.handler;

import com.cannon.nop.interfaces.config.exception.ApiException;
import com.cannon.nop.interfaces.config.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public abstract class JwtAuthAbstractHandler implements JwtAuthHandler {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_HEADER = "Bearer ";
    protected static final int MINIMUM_URL_LENGTH = 6;
    protected static final int MINIMUM_URL_INDEX = 5;
    protected static final int URI_UUID_LENGTH = 36;
    private JwtAuthHandler nextHandler;

    public void setNextHandler(JwtAuthHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle(JwtAuthParser jwtAuthParser) {
        process(jwtAuthParser);
        if (nextHandler != null) {
            nextHandler.handle(jwtAuthParser);
        }
    }

    protected abstract void process(JwtAuthParser jwtAuthParser);
    @Component
    public static class EntryPointHandler extends JwtAuthAbstractHandler{
        @Override
        protected void process(JwtAuthParser jwtAuthParser){
            parse(jwtAuthParser);
            extractJwtToken(jwtAuthParser);
        }
        private void parse(JwtAuthParser jwtAuthParser){
            String requestURI = jwtAuthParser.getRequest().getRequestURI();
            String [] parts = requestURI.split("/");
            String header = jwtAuthParser.getRequest().getHeader(AUTHORIZATION_HEADER);

            jwtAuthParser.setParts(parts);
            jwtAuthParser.setHeader(header);
        }
        private void extractJwtToken(JwtAuthParser jwtAuthParser){
            String header = jwtAuthParser.getHeader();
            if(header == null || !header.startsWith(BEARER_HEADER)){
                throw new ApiException(ErrorCode.AUTHORIZATION_HEADER_ERROR);
            }
            String jwtToken = header.substring(BEARER_HEADER.length());
            jwtAuthParser.setJwtToken(jwtToken);
        }
    }

    @Component
    public static class UrlValidationHandler extends JwtAuthAbstractHandler {
        @Override
        protected void process(JwtAuthParser jwtAuthParser) {
            validateRequestURL(jwtAuthParser.getParts());
        }

        private void validateRequestURL(String[] parts) {
            if (parts.length < MINIMUM_URL_LENGTH || parts[MINIMUM_URL_INDEX].length() != URI_UUID_LENGTH) {
                throw new ApiException(ErrorCode.NOT_FOUND_RESOURCE);
            }
        }
    }

    @Component
    public static class JwtValidationHandler extends JwtAuthAbstractHandler {
        @Override
        protected void process(JwtAuthParser jwtAuthParser) {
            String jwtToken = jwtAuthParser.getJwtToken();
            String eventUrlUUID = jwtAuthParser.getJwtProvider().extractEventUrlUUID(jwtToken);
            String[] parts = jwtAuthParser.getParts();
            String uriUUID = parts[MINIMUM_URL_INDEX];

            validateUrlUUIDAndTokenUUID(uriUUID, eventUrlUUID);

            if(jwtAuthParser.getJwtProvider().isTokenExpired(jwtToken)){
                throw new ApiException(ErrorCode.ACCESS_TOKEN_ERROR);
            }
        }

        public void validateUrlUUIDAndTokenUUID(String urlUUID, String eventUrlUUID) {
            if (!urlUUID.equals(eventUrlUUID)) {
                throw new ApiException(ErrorCode.ACCESS_TOKEN_ERROR);
            }
        }
    }
}

