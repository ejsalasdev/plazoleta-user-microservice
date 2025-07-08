package com.plazoleta.usermicroservice.domain.utils.validation.chain.impl;

import com.plazoleta.usermicroservice.domain.enums.RoleName;
import com.plazoleta.usermicroservice.domain.exceptions.InvalidElementFormatException;
import com.plazoleta.usermicroservice.domain.model.RoleModel;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.utils.validation.chain.UserDataValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class DocumentIdValidatorTest {

    static Object[][] documentIdCases() {
        return new Object[][] {
                { "12345678", true },
                { "00001234", true },
                { "1234A678", false },
                { "12-345678", false },
                { "", true },
                { null, true }
        };
    }

    @ParameterizedTest
    @MethodSource("documentIdCases")
    void when_documentIdHasValidOrInvalidFormat_then_validationBehavesAsExpected(String documentId,
            boolean shouldPass) {
        UserModel user = new UserModel(
                null,
                "Test",
                "User",
                documentId,
                "+573001234567",
                "1990-01-01",
                "test@mail.com",
                "plainpass",
                new RoleModel(2L, RoleName.OWNER, "User with seller role"));
        DocumentIdValidator validator = new DocumentIdValidator();
        if (shouldPass) {
            assertDoesNotThrow(() -> validator.validate(user));
        } else {
            assertThrows(InvalidElementFormatException.class, () -> validator.validate(user));
        }
    }

    @org.junit.jupiter.api.Test
    void when_documentIdValid_then_callsNextValidator() {
        DocumentIdValidator validator = new DocumentIdValidator();
        UserDataValidator next = org.mockito.Mockito.mock(UserDataValidator.class);
        validator.setNextValidator(next);
        UserModel user = new UserModel(
                null,
                "Test",
                "User",
                "12345678",
                "+573001234567",
                "1990-01-01",
                "test@mail.com",
                "plainpass",
                new RoleModel(2L, RoleName.OWNER, "User with seller role"));
        validator.validate(user);
        org.mockito.Mockito.verify(next, org.mockito.Mockito.times(1)).validate(user);
    }
}
