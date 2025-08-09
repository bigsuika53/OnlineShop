package com.bigsuika.onlineshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleQueryException(QueryException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found\n"+ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleGeneralException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error\n"+ex.getMessage());
    }
}
