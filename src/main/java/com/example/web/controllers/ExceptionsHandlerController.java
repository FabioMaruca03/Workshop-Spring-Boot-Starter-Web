package com.example.web.controllers;

import com.example.web.services.BookService.BookNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandlerController {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleRuntimeException(BookNotFoundException e) {
        return ResponseEntity.badRequest()
                .body("Thrown exception: %s".formatted(e.getLocalizedMessage()));
    }

}
