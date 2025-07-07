package com.plazoleta.usermicroservice.application.handler.impl;

import com.plazoleta.usermicroservice.application.dto.request.SaveUserRequest;
import com.plazoleta.usermicroservice.application.dto.response.SaveUserResponse;
import com.plazoleta.usermicroservice.application.handler.UserHandler;
import com.plazoleta.usermicroservice.application.mappers.UserRequestMapper;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.ports.in.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserHandlerImpl implements UserHandler {

    private final UserRequestMapper userRequestMapper;
    private final UserServicePort userServicePort;

    @Override
    public SaveUserResponse save(SaveUserRequest saveUserRequest) {
        UserModel userModel = userRequestMapper.requestToModel(saveUserRequest);
        userServicePort.save(userModel);
        return new SaveUserResponse("User created successfully", LocalDateTime.now());
    }

    @Override
    public SaveUserResponse getUserById(Long id) {
        UserModel userModel = userServicePort.getUserById(id);
        return new SaveUserResponse(
                "User found: " + userModel.getName() + " " + userModel.getLastName(),
                LocalDateTime.now());
    }
}
