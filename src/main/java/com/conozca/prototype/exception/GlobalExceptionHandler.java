package com.conozca.prototype.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthorAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleAuthorAlreadyExists(AuthorAlreadyExistsException ex) {
        ApiError error = new ApiError(409, "AUTHOR_ALREADY_EXISTS", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ApiError> handleAuthorNotFound(AuthorNotFoundException ex) {
        ApiError error = new ApiError(404, "AUTHOR_NOT_FOUND", "Autor no encontrado.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        ApiError error = new ApiError(500, "INTERNAL_ERROR", "Error inesperado: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}