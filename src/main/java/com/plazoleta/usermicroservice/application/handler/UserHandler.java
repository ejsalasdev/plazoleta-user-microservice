package com.plazoleta.usermicroservice.application.handler;

import com.plazoleta.usermicroservice.application.dto.request.SaveUserRequest;
import com.plazoleta.usermicroservice.application.dto.response.SaveUserResponse;

public interface UserHandler {
    
    SaveUserResponse save(SaveUserRequest saveUserRequest);
}
