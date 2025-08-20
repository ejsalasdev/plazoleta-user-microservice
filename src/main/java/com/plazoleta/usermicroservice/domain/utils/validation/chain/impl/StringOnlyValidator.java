package com.plazoleta.usermicroservice.domain.utils.validation.chain.impl;

import com.plazoleta.usermicroservice.domain.exceptions.InvalidElementFormatException;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainExceptionsConstants;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainConstants;
import com.plazoleta.usermicroservice.domain.utils.validation.chain.UserDataValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringOnlyValidator implements UserDataValidator {

    private UserDataValidator nextValidator;
    private final String fieldName;

    public StringOnlyValidator(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public void validate(UserModel userModel) {
        String value = "";
        if (fieldName.equals(DomainConstants.NAME_FIELD)) {
            value = userModel.getName();
        } else if (fieldName.equals(DomainConstants.LAST_NAME_FIELD)) {
            value = userModel.getLastName();
        }

        if (value != null && !value.trim().isEmpty()) {
            Pattern pattern = Pattern.compile(DomainConstants.ONLY_STRING_REGEX);
            Matcher matcher = pattern.matcher(value);
            if (!matcher.matches()) {
                throw new InvalidElementFormatException(
                        String.format(DomainExceptionsConstants.STRING_INPUT_FORMAT_ERROR_MESSAGE, fieldName, value)
                );
            }
        }

        if (nextValidator != null) {
            nextValidator.validate(userModel);
        }
    }

    @Override
    public void setNextValidator(UserDataValidator nextValidator) {
        this.nextValidator = nextValidator;
    }
}
