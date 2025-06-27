package com.plazoleta.usermicroservice.domain.utils.constants;

public final class DomainExceptionsConstants {

    public static final String USER_ALREADY_EXIST_MESSAGE = "User with documentId '%s' already exists";
    public static final String REQUIRED_FIELD_MESSAGE = "Field '%s' is necessary";
    public static final String STRING_INPUT_FORMAT_ERROR_MESSAGE = "Field %s '%s' contains invalid characters. Only letters and space are allowed.";
    public static final String DOCUMENT_ID_NOT_NUMERIC_MESSAGE = "Document ID must contain only numeric characters";
    public static final String DOCUMENT_ID_INVALID_LENGTH_MESSAGE = "Document ID length must be between 8 and 10 digits.";
    public static final String PHONE_NUMBER_INVALID_FORMAT_MESSAGE = "Phone number format is invalid (maximum 13 characters, can include '+')";
    public static final String PHONE_NUMBER_INVALID_LENGTH_MESSAGE = "Phone number length must be between 10 and 13 digits.";
    public static final String USER_UNDER_AGE_VALID_MESSAGE = "User must be of legal age";
    public static final String EMAIL_INVALID_FORMAT_MESSAGE = "Email format is invalid";

    private DomainExceptionsConstants() {
        throw new IllegalStateException("Utility Class");
    }
}