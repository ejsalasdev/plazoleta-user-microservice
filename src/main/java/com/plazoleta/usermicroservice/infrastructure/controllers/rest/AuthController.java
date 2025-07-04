package com.plazoleta.usermicroservice.infrastructure.controllers.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plazoleta.usermicroservice.application.dto.request.LoginRequest;
import com.plazoleta.usermicroservice.application.dto.response.LoginResponse;
import com.plazoleta.usermicroservice.application.handler.AuthHandler;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Operations related to login")
public class AuthController {
    
    private final AuthHandler authHandler;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(authHandler.login(request));
    }

}
