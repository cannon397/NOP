package com.cannon.nop.interfaces.config.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        // 내부 메시지 로깅
        log.error("getDeveloperMessage: {}", ex.getDeveloperMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ex.getErrorCode())
                .message(ex.getUserMessage())  // 사용자에게 노출할 메시지 사용
                .build();
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {

        String message = ex.getConstraintViolations()
                .stream().map(ConstraintViolation::getMessage).findFirst().orElseThrow(()->new RuntimeException("ConstraintViolationException 파싱오류"));

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCode.INVALID_INPUT.getCode())
                .message(message.toString())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        FieldError fieldError = ex.getBindingResult().getFieldError();
        String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : HttpStatus.BAD_REQUEST.getReasonPhrase();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCode.INVALID_INPUT.getCode())
                .message(errorMessage)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> noHandlerFoundException(NoHandlerFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCode.NOT_FOUND_RESOURCE.getCode())
                .message(ErrorCode.NOT_FOUND_RESOURCE.getUserMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {

        log.error("UnCheckedException: ", ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCode.INTERNAL_ERROR.getCode())
                .message(ErrorCode.INTERNAL_ERROR.getUserMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
