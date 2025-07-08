package com.plazoleta.usermicroservice.domain.utils.validation.chain.impl;

import com.plazoleta.usermicroservice.domain.enums.RoleName;
import com.plazoleta.usermicroservice.domain.exceptions.InvalidElementLengthException;
import com.plazoleta.usermicroservice.domain.model.RoleModel;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.utils.validation.chain.UserDataValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberLengthValidatorTest {

    static Object[][] phoneNumberLengthCases() {
        return new Object[][] {
                { "+1234567890", true },
                { "1234567890", true },
                { "+123456789012", true },
                { "+123456789", true },
                { "123456789", false },
                { "+1234567890123", false },
                { null, true },
                { "", true }
        };
    }

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
                new RoleModel(2L, RoleName.OWNER, "User with seller role"));
    }

    @ParameterizedTest
    @MethodSource("phoneNumberLengthCases")
    void when_phoneNumberHasValidOrInvalidLength_then_validationBehavesAsExpected(String phone, boolean shouldPass) {
        UserModel user = buildUserWithPhone(phone);
        PhoneNumberLengthValidator validator = new PhoneNumberLengthValidator();
        if (shouldPass) {
            assertDoesNotThrow(() -> validator.validate(user));
        } else {
            assertThrows(InvalidElementLengthException.class, () -> validator.validate(user));
        }
    }

    @org.junit.jupiter.api.Test
    void when_phoneNumberLengthValid_then_callsNextValidator() {
        PhoneNumberLengthValidator validator = new PhoneNumberLengthValidator();
        UserDataValidator next = org.mockito.Mockito.mock(UserDataValidator.class);
        validator.setNextValidator(next);
        UserModel user = buildUserWithPhone("+573001234567");
        validator.validate(user);
        org.mockito.Mockito.verify(next, org.mockito.Mockito.times(1)).validate(user);
    }
}
