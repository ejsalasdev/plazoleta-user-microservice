package com.plazoleta.usermicroservice.domain.utils.validation;

import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainConstants;
import com.plazoleta.usermicroservice.domain.utils.validation.chain.UserDataValidator;
import com.plazoleta.usermicroservice.domain.utils.validation.chain.impl.*;

public class UserValidatorChain {
    
    private final UserDataValidator firstValidator;

    public UserValidatorChain() {
        
        UserDataValidator requiredFieldsValidator = new RequiredFieldsValidator();
        UserDataValidator stringOnlyInNameValidator = new StringOnlyValidator(DomainConstants.NAME_FIELD);
        UserDataValidator stringOnlyInLastNameValidator = new StringOnlyValidator(DomainConstants.LAST_NAME_FIELD);
        UserDataValidator documentIdValidator = new DocumentIdValidator();
        UserDataValidator documentIdLengthValidator = new DocumentIdLengthValidator();
        UserDataValidator phoneNumberValidator = new PhoneNumberValidator();
        UserDataValidator phoneNumberLengthValidator = new PhoneNumberLengthValidator();
        UserDataValidator requiredAgeValidator = new RequiredAgeValidator();
        UserDataValidator emailFormatValidator = new EmailFormatValidator();
        
        requiredFieldsValidator.setNextValidator(stringOnlyInNameValidator);
        stringOnlyInNameValidator.setNextValidator(stringOnlyInLastNameValidator);
        stringOnlyInLastNameValidator.setNextValidator(documentIdValidator);
        documentIdValidator.setNextValidator(documentIdLengthValidator);
        documentIdLengthValidator.setNextValidator(phoneNumberValidator);
        phoneNumberValidator.setNextValidator(phoneNumberLengthValidator);
        phoneNumberLengthValidator.setNextValidator(requiredAgeValidator);
        requiredAgeValidator.setNextValidator(emailFormatValidator);
        
        this.firstValidator = requiredFieldsValidator;
    }

    public void validate(UserModel userModel) {
        if (firstValidator != null) {
            firstValidator.validate(userModel);
        }
    }
}
