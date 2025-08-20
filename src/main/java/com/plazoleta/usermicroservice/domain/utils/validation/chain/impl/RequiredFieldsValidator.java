package com.plazoleta.usermicroservice.domain.utils.validation.chain.impl;

import com.plazoleta.usermicroservice.domain.exceptions.RequiredFieldsException;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainExceptionsConstants;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainConstants;
import com.plazoleta.usermicroservice.domain.utils.validation.chain.UserDataValidator;

public class RequiredFieldsValidator implements UserDataValidator {

    private UserDataValidator nextValidator;

    @Override
    public void validate(UserModel userModel) {
        if (userModel.getName() == null || userModel.getName().trim().isEmpty()) {
            throw new RequiredFieldsException(
                    String.format(DomainExceptionsConstants.REQUIRED_FIELD_MESSAGE, DomainConstants.NAME_FIELD)
            );
        }
        if (userModel.getLastName() == null || userModel.getLastName().trim().isEmpty()) {
            throw new RequiredFieldsException(
                    String.format(DomainExceptionsConstants.REQUIRED_FIELD_MESSAGE, DomainConstants.LAST_NAME_FIELD)
            );
        }
        if (userModel.getDocumentId() == null || userModel.getDocumentId().trim().isEmpty()) {
            throw new RequiredFieldsException(
                    String.format(DomainExceptionsConstants.REQUIRED_FIELD_MESSAGE, DomainConstants.DOCUMENT_ID_FIELD)
            );
        }
        if (userModel.getPhoneNumber() == null || userModel.getPhoneNumber().trim().isEmpty()) {
            throw new RequiredFieldsException(
                    String.format(DomainExceptionsConstants.REQUIRED_FIELD_MESSAGE, DomainConstants.PHONE_NUMBER_FIELD)
            );
        }
        if (userModel.getBirthDate() == null) {
            throw new RequiredFieldsException(
                    String.format(DomainExceptionsConstants.REQUIRED_FIELD_MESSAGE, DomainConstants.BIRTH_DATE_FIELD)
            );
        }
        if (userModel.getEmail() == null || userModel.getEmail().trim().isEmpty()) {
            throw new RequiredFieldsException(
                    String.format(DomainExceptionsConstants.REQUIRED_FIELD_MESSAGE, DomainConstants.EMAIL_FIELD)
            );
        }
        if (userModel.getPassword() == null || userModel.getPassword().trim().isEmpty()) {
            throw new RequiredFieldsException(
                    String.format(DomainExceptionsConstants.REQUIRED_FIELD_MESSAGE, DomainConstants.PASSWORD_FIELD)
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
