package com.plazoleta.usermicroservice.domain.utils.validation.chain.impl;

import com.plazoleta.usermicroservice.domain.exceptions.InvalidElementFormatException;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainExceptionsConstants;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainConstants;
import com.plazoleta.usermicroservice.domain.utils.validation.chain.UserDataValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailFormatValidator implements UserDataValidator {
    
    private UserDataValidator nextValidator;
    @Override
    public void validate(UserModel userModel) {

        if (userModel.getEmail() != null && !userModel.getEmail().trim().isEmpty()) {
            String regex = DomainConstants.EMAIL_FORMAT_REGEX;
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(userModel.getEmail());
            if (!matcher.matches()) {
                throw new InvalidElementFormatException(
                        String.format(DomainExceptionsConstants.EMAIL_INVALID_FORMAT_MESSAGE)
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
