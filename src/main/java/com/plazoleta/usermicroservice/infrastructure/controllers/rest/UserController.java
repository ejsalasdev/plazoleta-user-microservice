package com.plazoleta.usermicroservice.infrastructure.controllers.rest;

import com.plazoleta.usermicroservice.application.dto.request.SaveUserRequest;
import com.plazoleta.usermicroservice.application.dto.response.SaveUserResponse;
import com.plazoleta.usermicroservice.application.handler.UserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "Operations related to users")
public class UserController {

    private final UserHandler userHandler;

    @Operation(summary = "Create a new owner user")
    @PostMapping("/")
    public ResponseEntity<SaveUserResponse> save(@RequestBody SaveUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userHandler.save(request));
    }
}
