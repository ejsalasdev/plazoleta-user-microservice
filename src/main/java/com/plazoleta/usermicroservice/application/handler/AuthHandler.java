package com.plazoleta.usermicroservice.application.handler;

import com.plazoleta.usermicroservice.application.dto.request.LoginRequest;
import com.plazoleta.usermicroservice.application.dto.response.LoginResponse;

public interface AuthHandler {
    
    LoginResponse login(LoginRequest loginRequest);
}
