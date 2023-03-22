package org.artel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.NotFound;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<?> notFoundException(NotFound notFound) {
        return ResponseEntity.notFound().build();
    }
}
