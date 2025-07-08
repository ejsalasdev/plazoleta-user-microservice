package com.plazoleta.usermicroservice.infrastructure.exceptions;

public class UserNotFoundException extends IllegalStateException {
    
    public UserNotFoundException(String s) {
        super(s);
    }
}
