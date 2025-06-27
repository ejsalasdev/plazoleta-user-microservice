package com.plazoleta.usermicroservice.domain.ports.in;

import com.plazoleta.usermicroservice.domain.model.UserModel;

public interface UserServicePort {
    
    void save(UserModel userModel);
}
