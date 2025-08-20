package com.plazoleta.usermicroservice.domain.utils.validation.chain.impl;

import com.plazoleta.usermicroservice.domain.enums.RoleName;
import com.plazoleta.usermicroservice.domain.exceptions.InvalidElementFormatException;
import com.plazoleta.usermicroservice.domain.model.RoleModel;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainConstants;
import com.plazoleta.usermicroservice.domain.utils.validation.chain.UserDataValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class StringOnlyValidatorTest {

    static Object[][] stringOnlyCases() {
        return new Object[][] {
                { DomainConstants.NAME_FIELD, "Juan", true },
                { DomainConstants.NAME_FIELD, "María José", true },
                { DomainConstants.NAME_FIELD, "José Álvarez", true },
                { DomainConstants.NAME_FIELD, "Ana María", true },
                { DomainConstants.NAME_FIELD, "Juan123", false },
                { DomainConstants.NAME_FIELD, "Juan!", false },
                { DomainConstants.NAME_FIELD, "", true },
                { DomainConstants.NAME_FIELD, null, true },
                { DomainConstants.LAST_NAME_FIELD, "Pérez", true },
                { DomainConstants.LAST_NAME_FIELD, "García López", true },
                { DomainConstants.LAST_NAME_FIELD, "García123", false },
                { DomainConstants.LAST_NAME_FIELD, "López!", false },
                { DomainConstants.LAST_NAME_FIELD, "", true },
                { DomainConstants.LAST_NAME_FIELD, null, true }
        };
    }

    @ParameterizedTest
    @MethodSource("stringOnlyCases")
    void when_fieldHasValidOrInvalidCharacters_then_validationBehavesAsExpected(String field, String value,
            boolean shouldPass) {
        String name = field.equals(DomainConstants.NAME_FIELD) ? value : "Nombre";
        String lastName = field.equals(DomainConstants.LAST_NAME_FIELD) ? value : "Apellido";
        UserModel user = new UserModel(
                null,
                name,
                lastName,
                "12345678",
                "+573001234567",
                "1990-01-01",
                "test@mail.com",
                "plainpass",
                new RoleModel(2L, RoleName.OWNER, "User with seller role"), null);
        StringOnlyValidator validator = new StringOnlyValidator(field);
        if (shouldPass) {
            assertDoesNotThrow(() -> validator.validate(user));
        } else {
            assertThrows(InvalidElementFormatException.class, () -> validator.validate(user));
        }
    }

    @org.junit.jupiter.api.Test
    void when_stringOnlyFieldValid_then_callsNextValidator() {
        StringOnlyValidator validator = new StringOnlyValidator(DomainConstants.NAME_FIELD);
        UserDataValidator next = org.mockito.Mockito.mock(UserDataValidator.class);
        validator.setNextValidator(next);
        UserModel user = new UserModel(
                null,
                "Juan",
                "Apellido",
                "12345678",
                "+573001234567",
                "1990-01-01",
                "test@mail.com",
                "plainpass",
                new RoleModel(2L, RoleName.OWNER, "User with seller role"), null);
        validator.validate(user);
        org.mockito.Mockito.verify(next, org.mockito.Mockito.times(1)).validate(user);
    }
}
