package com.plazoleta.usermicroservice.domain.exceptions;

public class RequiredFieldsException extends IllegalArgumentException{

    public RequiredFieldsException(String s) {
        super(s);
    }

    public RequiredFieldsException(String message, String fieldName) {
        super(String.format(message, fieldName));
    }
}
