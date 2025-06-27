package com.plazoleta.usermicroservice.domain.utils.validation.chain.impl;

import com.plazoleta.usermicroservice.domain.exceptions.InvalidElementLengthException;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainConstants;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainExceptionsConstants;
import com.plazoleta.usermicroservice.domain.utils.validation.chain.UserDataValidator;

public class DocumentIdLengthValidator implements UserDataValidator {

    private UserDataValidator nextValidator;

    @Override
    public void validate(UserModel userModel) {
        String documentId = userModel.getDocumentId();
        if (documentId != null && !documentId.trim().isEmpty() &&
                (documentId.length() < DomainConstants.DOCUMENT_ID_MIN_LENGTH ||
                        documentId.length() > DomainConstants.DOCUMENT_ID_MAX_LENGTH)) {
            throw new InvalidElementLengthException(
                    String.format(DomainExceptionsConstants.DOCUMENT_ID_INVALID_LENGTH_MESSAGE)
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
