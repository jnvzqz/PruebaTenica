package com.jv.spring.PruebaTenica.Advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jv.spring.PruebaTenica.Exception.SuperheroNotFoundException;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({SuperheroNotFoundException.class})
    public ResponseEntity<Object> handleSuperheroNotFoundException(SuperheroNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}