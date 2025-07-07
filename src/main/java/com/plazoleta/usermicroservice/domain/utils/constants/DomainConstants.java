package com.plazoleta.usermicroservice.domain.utils.constants;

import com.plazoleta.usermicroservice.domain.enums.RoleName;
import com.plazoleta.usermicroservice.domain.model.RoleModel;

public final class DomainConstants {

    public static final RoleModel OWNER_ROLE = new RoleModel(2L, RoleName.OWNER, "User with seller role");
    public static final Integer DOCUMENT_ID_MAX_LENGTH = 10;
    public static final Integer DOCUMENT_ID_MIN_LENGTH = 8;
    public static final String ONLY_NUMERIC_REGEX = "^\\d+$";
    public static final String ONLY_STRING_REGEX = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";
    public static final String EMAIL_FORMAT_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$";
    public static final Integer PHONE_NUMBER_MAX_LENGTH = 13;
    public static final Integer PHONE_NUMBER_MIN_LENGTH = 10;
    public static final String PHONE_NUMBER_FORMAT_REGEX = "^\\+?\\d+$";
    public static final String TIME_ZONE = "America/Bogota";
    public static final Integer ADULT_AGE = 18;

    public static final String NAME_FIELD = "name";
    public static final String LAST_NAME_FIELD = "lastName";
    public static final String DOCUMENT_ID_FIELD = "documentId";
    public static final String PHONE_NUMBER_FIELD = "phoneNumber";
    public static final String BIRTH_DATE_FIELD = "birthDate";
    public static final String EMAIL_FIELD = "email";
    public static final String PASSWORD_FIELD = "password";

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_OWNER = "OWNER";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";

    private DomainConstants() {

        throw new IllegalStateException("Utility Class");
    }
}
