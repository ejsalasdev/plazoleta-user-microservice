package com.plazoleta.usermicroservice.domain.exceptions;

public class ElementAlreadyExistsException extends RuntimeException{

    public ElementAlreadyExistsException(String s) {
        super(s);
    }
}
