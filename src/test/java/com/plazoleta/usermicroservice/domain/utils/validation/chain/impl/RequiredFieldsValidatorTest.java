package com.plazoleta.usermicroservice.domain.utils.validation.chain.impl;

import com.plazoleta.usermicroservice.domain.enums.RoleName;
import com.plazoleta.usermicroservice.domain.exceptions.RequiredFieldsException;
import com.plazoleta.usermicroservice.domain.model.RoleModel;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class RequiredFieldsValidatorTest {

    private RequiredFieldsValidator validator;
    private UserModel validUser;

    @BeforeEach
    void setUp() {
        validator = new RequiredFieldsValidator();
        validUser = new UserModel(
                null,
                "Test",
                "User",
                "12345678",
                "+573001234567",
                "1990-01-01",
                "test@mail.com",
                "plainpass",
                new RoleModel(2L, RoleName.OWNER, "User with seller role"));
    }

    @Test
    void when_allFieldsPresent_then_noExceptionThrown() {
        assertDoesNotThrow(() -> validator.validate(validUser));
    }

    static Object[][] requiredFieldsCases() {
        return new Object[][] {
                { "name", null, "User", "12345678", "+573001234567", "1990-01-01", "test@mail.com", "plainpass" },
                { "name", "", "User", "12345678", "+573001234567", "1990-01-01", "test@mail.com", "plainpass" },
                { "lastName", "Test", null, "12345678", "+573001234567", "1990-01-01", "test@mail.com", "plainpass" },
                { "lastName", "Test", "", "12345678", "+573001234567", "1990-01-01", "test@mail.com", "plainpass" },
                { "documentId", "Test", "User", null, "+573001234567", "1990-01-01", "test@mail.com", "plainpass" },
                { "documentId", "Test", "User", "", "+573001234567", "1990-01-01", "test@mail.com", "plainpass" },
                { "phoneNumber", "Test", "User", "12345678", null, "1990-01-01", "test@mail.com", "plainpass" },
                { "phoneNumber", "Test", "User", "12345678", "", "1990-01-01", "test@mail.com", "plainpass" },
                { "birthDate", "Test", "User", "12345678", "+573001234567", null, "test@mail.com", "plainpass" },
                { "email", "Test", "User", "12345678", "+573001234567", "1990-01-01", null, "plainpass" },
                { "email", "Test", "User", "12345678", "+573001234567", "1990-01-01", "", "plainpass" },
                { "password", "Test", "User", "12345678", "+573001234567", "1990-01-01", "test@mail.com", null },
                { "password", "Test", "User", "12345678", "+573001234567", "1990-01-01", "test@mail.com", "" }
        };
    }

    @ParameterizedTest
    @MethodSource("requiredFieldsCases")
    void when_requiredFieldIsNullOrEmpty_then_throwRequiredFieldsException(String field, String name, String lastName,
            String documentId, String phoneNumber, String birthDate, String email, String password) {
        UserModel user = new UserModel(
                null,
                name,
                lastName,
                documentId,
                phoneNumber,
                birthDate,
                email,
                password,
                new RoleModel(2L, RoleName.OWNER, "User with seller role"));
        RequiredFieldsException ex = assertThrows(RequiredFieldsException.class, () -> validator.validate(user));
        assertTrue(ex.getMessage().toLowerCase().contains(field.toLowerCase()));
    }
}
