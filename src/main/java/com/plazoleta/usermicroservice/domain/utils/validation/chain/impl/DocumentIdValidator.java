package com.plazoleta.usermicroservice.domain.utils.validation.chain.impl;

import com.plazoleta.usermicroservice.domain.exceptions.InvalidElementFormatException;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainExceptionsConstants;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainConstants;
import com.plazoleta.usermicroservice.domain.utils.validation.chain.UserDataValidator;

public class DocumentIdValidator implements UserDataValidator {

    private UserDataValidator nextValidator;

    @Override
    public void validate(UserModel userModel) {
        if (userModel.getDocumentId() != null && !userModel.getDocumentId().trim().isEmpty() &&
                !userModel.getDocumentId().matches(DomainConstants.ONLY_NUMERIC_REGEX)) {
            throw new InvalidElementFormatException(
                    String.format(DomainExceptionsConstants.DOCUMENT_ID_NOT_NUMERIC_MESSAGE)
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
