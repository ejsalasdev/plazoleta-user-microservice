package com.plazoleta.usermicroservice.domain.exceptions;

public class RoleNotAllowedException extends IllegalStateException {
    
    public RoleNotAllowedException(String s) {
        super(s);
    }
}
