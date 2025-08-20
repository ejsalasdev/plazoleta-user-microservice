package com.plazoleta.usermicroservice.domain.utils.validation.chain.impl;

import com.plazoleta.usermicroservice.domain.exceptions.InvalidElementFormatException;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainExceptionsConstants;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainConstants;
import com.plazoleta.usermicroservice.domain.utils.validation.chain.UserDataValidator;

public class PhoneNumberValidator implements UserDataValidator {

    private UserDataValidator nextValidator;

    @Override
    public void validate(UserModel userModel) {
        if (userModel.getPhoneNumber() != null && !userModel.getPhoneNumber().trim().isEmpty() &&
                !userModel.getPhoneNumber().matches(DomainConstants.PHONE_NUMBER_FORMAT_REGEX)) {
            throw new InvalidElementFormatException(
                    String.format(DomainExceptionsConstants.PHONE_NUMBER_INVALID_FORMAT_MESSAGE)
            );
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
