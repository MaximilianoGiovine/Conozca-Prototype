package com.conozca.prototype.exception;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException() {
        super("Autor no encontrado.");
    }

    public AuthorNotFoundException(String message) {
        super(message);
    }

}
