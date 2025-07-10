package com.plazoleta.usermicroservice.domain.utils.validation.chain.impl;

import com.plazoleta.usermicroservice.domain.exceptions.InvalidElementFormatException;
import com.plazoleta.usermicroservice.domain.enums.RoleName;
import com.plazoleta.usermicroservice.domain.model.RoleModel;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.utils.validation.chain.UserDataValidator;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberValidatorTest {

    private final PhoneNumberValidator validator = new PhoneNumberValidator();


    private UserModel buildUserWithPhone(String phone) {
        return new UserModel(
                null,
                "Test",
                "User",
                "12345678",
                phone,
                "1990-01-01",
                "test@mail.com",
                "plainpass",
                new RoleModel(2L, RoleName.OWNER, "User with seller role"),
                null
        );
    }

    static Object[][] phoneNumberCases() {
        return new Object[][] {
                { "+1234567890", true },
                { "1234567890", true },
                { "+1", true },
                { "123", true },
                { "+1234567890123", true },
                { "+12-345", false },
                { "12 345", false },
                { "abc123", false },
                { "+123abc", false },
                { "123_456", false },
                { "(123)4567890", false },
                { "123.456.7890", false },
                { "++123456", false },
                { null, true },
                { "", true }
        };
    }

    @ParameterizedTest
    @org.junit.jupiter.params.provider.MethodSource("phoneNumberCases")
    void when_phoneNumberHasValidOrInvalidFormat_then_validationBehavesAsExpected(String phone, boolean shouldPass) {
        UserModel user = buildUserWithPhone(phone);
        if (shouldPass) {
            assertDoesNotThrow(() -> validator.validate(user));
        } else {
            assertThrows(InvalidElementFormatException.class, () -> validator.validate(user));
        }
    }

    @org.junit.jupiter.api.Test
    void when_phoneNumberValid_then_callsNextValidator() {
        PhoneNumberValidator localValidator = new PhoneNumberValidator();
        UserDataValidator next = org.mockito.Mockito.mock(UserDataValidator.class);
        localValidator.setNextValidator(next);
        UserModel user = buildUserWithPhone("+573001234567");
        localValidator.validate(user);
        org.mockito.Mockito.verify(next, org.mockito.Mockito.times(1)).validate(user);
    }
}
