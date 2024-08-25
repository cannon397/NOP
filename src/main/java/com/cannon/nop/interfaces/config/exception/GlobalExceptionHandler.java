package com.cannon.nop.interfaces.config.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        // 내부 메시지 로깅
        log.error("ApiException: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(String.valueOf(ex.getStatus().value()))
                .message(ex.getUserMessage())  // 사용자에게 노출할 메시지 사용
                .build();

        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleArgumentException(IllegalArgumentException ex) {
        log.error("IllegalArgumentException: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message(ex.getMessage())
                .build();
        log.error("NullPointerException", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
//  예상되는 서버 예외
    @ExceptionHandler(value = InternalServerException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerException(InternalServerException ex) {
        log.error("InternalServerException: {}", ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("500")
                .message("invalid error")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("500")
                .message("invalid error")
                .build();
        log.error("UnCheckedException: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
