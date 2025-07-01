package com.plazoleta.usermicroservice.domain.utils.validation.chain.impl;

import com.plazoleta.usermicroservice.domain.enums.RoleName;
import com.plazoleta.usermicroservice.domain.exceptions.InvalidElementFormatException;
import com.plazoleta.usermicroservice.domain.model.RoleModel;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class EmailFormatValidatorTest {

    static Object[][] emailCases() {
        return new Object[][] {
                { "user@example.com", true },
                { "user.name+tag@sub.domain.com", true },
                { "user_name@domain.co", true },
                { "user@domain", false },
                { "user@.com", false },
                { "@domain.com", false },
                { "user@domain..com", false },
                { "user@domain.c", false },
                { "user@domain.corporate", true },
                { "", true },
                { null, true }
        };
    }

    private UserModel buildUserWithEmail(String email) {
        return new UserModel(
                null,
                "Test",
                "User",
                "12345678",
                "+1234567890",
                "2000-01-01",
                email,
                "plainpass",
                new RoleModel(2L, RoleName.OWNER, "User with seller role"));
    }

    @ParameterizedTest
    @MethodSource("emailCases")
    void when_emailHasValidOrInvalidFormat_then_validationBehavesAsExpected(String email, boolean shouldPass) {
        UserModel user = buildUserWithEmail(email);
        EmailFormatValidator validator = new EmailFormatValidator();
        if (shouldPass) {
            assertDoesNotThrow(() -> validator.validate(user));
        } else {
            assertThrows(InvalidElementFormatException.class, () -> validator.validate(user));
        }
    }
}
