package com.mmozhzhe.featuretoggle.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    private static final String TIMESTAMP_FIELD = "timestamp";
    public static final String MESSAGE = "message";

    @ExceptionHandler(FeatureToggleWebException.class)
    public ResponseEntity<Object> handleWebServiceException(
            FeatureToggleWebException ex, WebRequest request) {
        log.error("Exception log: ", ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_FIELD, LocalDateTime.now());
        String message = "Error message " + ex.getMessage();
        body.put(MESSAGE, message);
        body.put("errorCode", ex.getErrorCode());

        log.info("Handling FeatureToggleWebException [{}] and returning the error response {}", ex.getMessage(), message);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(
            RuntimeException ex, WebRequest request) {
        log.error("Exception log: ", ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_FIELD, LocalDateTime.now());
        String message = "Error message " + ex.getMessage();
        body.put(MESSAGE, message);

        log.info("Handling any runtime exception [{}] and returning the error response {}", ex.getMessage(), message);

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
