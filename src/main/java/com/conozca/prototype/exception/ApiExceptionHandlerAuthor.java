package com.conozca.prototype.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class ApiExceptionHandlerAuthor {
    @ExceptionHandler(AuthorAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleAuthorAlreadyExists(AuthorAlreadyExistsException ex) {
        ApiError error = new ApiError(409, "AUTHOR_ALREADY_EXISTS", ex.getMessage());
        System.out.println("Entró al handler con mensaje: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }



    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<String> handleAuthorNotFound(AuthorNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Autor no encontrado.");
    }

    /* Opcional: para capturar errores genéricos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno: " + ex.getMessage());
    }
    */
}
