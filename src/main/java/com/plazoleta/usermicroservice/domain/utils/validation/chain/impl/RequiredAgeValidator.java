package com.plazoleta.usermicroservice.domain.utils.validation.chain.impl;

import com.plazoleta.usermicroservice.domain.exceptions.UnderAgeException;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainExceptionsConstants;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainConstants;
import com.plazoleta.usermicroservice.domain.utils.validation.chain.UserDataValidator;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.plazoleta.usermicroservice.domain.exceptions.InvalidElementFormatException;

public class RequiredAgeValidator implements UserDataValidator {

    private UserDataValidator nextValidator;

    @Override
    public void validate(UserModel userModel) {
        if (userModel.getBirthDate() != null && !userModel.getBirthDate().trim().isEmpty()) {
            LocalDate birthDate;
            try {
                birthDate = LocalDate.parse(userModel.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                throw new InvalidElementFormatException("Birth date must be in format yyyy-MM-dd");
            }
            LocalDate now = LocalDate.now(ZoneId.of(DomainConstants.TIME_ZONE));
            Period period = Period.between(birthDate, now);
            if (period.getYears() < DomainConstants.ADULT_AGE) {
                throw new UnderAgeException(
                        String.format(DomainExceptionsConstants.USER_UNDER_AGE_VALID_MESSAGE, DomainConstants.ADULT_AGE)
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
