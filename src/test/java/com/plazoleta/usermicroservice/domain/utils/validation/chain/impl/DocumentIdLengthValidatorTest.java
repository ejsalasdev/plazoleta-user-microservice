package com.plazoleta.usermicroservice.domain.utils.validation.chain.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.plazoleta.usermicroservice.domain.enums.RoleName;
import com.plazoleta.usermicroservice.domain.exceptions.InvalidElementLengthException;
import com.plazoleta.usermicroservice.domain.model.RoleModel;
import com.plazoleta.usermicroservice.domain.model.UserModel;

class DocumentIdLengthValidatorTest {

    static Object[][] documentIdLengthCases() {
        return new Object[][] {
                { "12345678", true },
                { "1234567890", true },
                { "1234567", false },
                { "12345678901", false },
                { "", true },
                { null, true }
        };
    }

    @ParameterizedTest
    @MethodSource("documentIdLengthCases")
    void when_documentIdHasValidOrInvalidLength_then_validationBehavesAsExpected(String documentId,
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
        DocumentIdLengthValidator validator = new DocumentIdLengthValidator();
        if (shouldPass) {
            assertDoesNotThrow(() -> validator.validate(user));
        } else {
            assertThrows(InvalidElementLengthException.class, () -> validator.validate(user));
        }
    }
}
