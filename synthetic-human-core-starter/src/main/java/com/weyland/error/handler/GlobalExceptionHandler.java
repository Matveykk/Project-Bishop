package com.weyland.error.handler;

import com.weyland.error.exception.CommandValidationException;
import com.weyland.error.exception.QueueOverflowException;
import com.weyland.error.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private String now() {
        return OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    @ExceptionHandler(CommandValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(CommandValidationException ex) {
        log.warn("Validation error: {}", ex.getMessage());
        ErrorResponse response = new ErrorResponse("Validation Error", ex.getMessage(), now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QueueOverflowException.class)
    public ResponseEntity<ErrorResponse> handleQueueOverflowException(CommandValidationException ex) {
        log.warn("Queue push error: {}", ex.getMessage());
        ErrorResponse response = new ErrorResponse("QueueOverflow Error", ex.getMessage(), now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
