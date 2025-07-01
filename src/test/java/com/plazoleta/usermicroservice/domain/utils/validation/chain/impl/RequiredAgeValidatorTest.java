package com.plazoleta.usermicroservice.domain.utils.validation.chain.impl;

import com.plazoleta.usermicroservice.domain.enums.RoleName;
import com.plazoleta.usermicroservice.domain.exceptions.InvalidElementFormatException;
import com.plazoleta.usermicroservice.domain.exceptions.UnderAgeException;
import com.plazoleta.usermicroservice.domain.model.RoleModel;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class RequiredAgeValidatorTest {

    static Object[][] birthDateCases() {
        return new Object[][] {
                { "2000-01-01", true },
                { "2010-01-01", false },
                { "", true },
                { null, true },
                { "not-a-date", false }
        };
    }

    private UserModel buildUserWithBirthDate(String birthDate) {
        return new UserModel(
                null,
                "Test",
                "User",
                "12345678",
                "+1234567890",
                birthDate,
                "test@mail.com",
                "plainpass",
                new RoleModel(2L, RoleName.OWNER, "User with seller role"));
    }

    @ParameterizedTest
    @MethodSource("birthDateCases")
    void when_birthDateIsValidOrInvalid_then_validationBehavesAsExpected(String birthDate, boolean shouldPass) {
        UserModel user = buildUserWithBirthDate(birthDate);
        RequiredAgeValidator validator = new RequiredAgeValidator();
        if (birthDate != null && !birthDate.trim().isEmpty() && !birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            assertThrows(InvalidElementFormatException.class, () -> validator.validate(user));
        } else if (shouldPass) {
            assertDoesNotThrow(() -> validator.validate(user));
        } else {
            assertThrows(UnderAgeException.class, () -> validator.validate(user));
        }
    }
}
