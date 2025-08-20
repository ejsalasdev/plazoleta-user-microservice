package com.plazoleta.usermicroservice.domain.exceptions;

public class UnderAgeException extends IllegalArgumentException{

    public UnderAgeException(String s) {
        super(s);
    }
}
