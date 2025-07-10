package com.plazoleta.usermicroservice.domain.exceptions;

public class AuthenticationContextException extends RuntimeException {
    
    public AuthenticationContextException(String message) {
        super(message);
    }
    
    public AuthenticationContextException(String message, Throwable cause) {
        super(message, cause);
    }
}
