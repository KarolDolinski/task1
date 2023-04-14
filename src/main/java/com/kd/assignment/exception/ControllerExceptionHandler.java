package com.kd.assignment.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {TransactionException.class})
    public ResponseEntity<Object> handleTransactionException(Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}