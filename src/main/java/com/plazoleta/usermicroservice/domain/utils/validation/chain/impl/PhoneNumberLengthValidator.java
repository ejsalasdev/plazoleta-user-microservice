package com.plazoleta.usermicroservice.domain.utils.validation.chain.impl;

import com.plazoleta.usermicroservice.domain.exceptions.InvalidElementLengthException;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainExceptionsConstants;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainConstants;
import com.plazoleta.usermicroservice.domain.utils.validation.chain.UserDataValidator;

public class PhoneNumberLengthValidator implements UserDataValidator {

    private UserDataValidator nextValidator;

    @Override
    public void validate(UserModel userModel) {
        String phoneNumber = userModel.getPhoneNumber();

        if (phoneNumber != null && !phoneNumber.trim().isEmpty() &&
                (phoneNumber.length() < DomainConstants.PHONE_NUMBER_MIN_LENGTH ||
                        phoneNumber.length() > DomainConstants.PHONE_NUMBER_MAX_LENGTH)) {
            throw new InvalidElementLengthException(
                    String.format(DomainExceptionsConstants.PHONE_NUMBER_INVALID_LENGTH_MESSAGE)
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
