package com.aios.bananesexport.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class RestApiErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(HttpServletRequest request, Exception ex) {
        Error error = Error.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .message(ex.getMessage())
                .url(request.getRequestURL()
                        .toString())
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> handleResourceNotFoundException(HttpServletRequest request,
                                                                 ResourceNotFoundException ex) {
        Error error = Error.builder()
                .errorCode(HttpStatus.NOT_FOUND.toString())
                .message(ex.getMessage())
                .url(request.getRequestURL()
                        .toString())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Error> handleGlobalRuntimeException2(HttpServletRequest request, BusinessException ex) {
        Error error = Error.builder()
                .errorCode(HttpStatus.CONFLICT.toString())
                .message(ex.getMessage())
                .url(request.getRequestURL()
                        .toString())
                .build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

}