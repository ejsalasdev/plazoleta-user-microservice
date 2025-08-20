package com.plazoleta.usermicroservice.domain.utils.validation.chain;

import com.plazoleta.usermicroservice.domain.model.UserModel;

public interface UserDataValidator {
    
    void validate(UserModel userModel);
    void setNextValidator(UserDataValidator nextValidator);
}
