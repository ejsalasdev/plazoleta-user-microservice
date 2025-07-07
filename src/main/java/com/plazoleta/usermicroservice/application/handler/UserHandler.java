package com.plazoleta.usermicroservice.application.handler;

import com.plazoleta.usermicroservice.application.dto.request.SaveUserRequest;
import com.plazoleta.usermicroservice.application.dto.response.SaveUserResponse;
import com.plazoleta.usermicroservice.application.dto.response.UserInfoResponse;

public interface UserHandler {
    SaveUserResponse save(SaveUserRequest saveUserRequest);

    UserInfoResponse getUserById(Long id);
}
