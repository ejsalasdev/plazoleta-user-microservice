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
import com.plazoleta.usermicroservice.infrastructure.exceptionhandler.ExceptionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Operations related to login")
public class AuthController {
    
    private final AuthHandler authHandler;

    @PostMapping("/")
    @Operation(
        summary = "Login",
        description = "Authenticate a user and return a JWT token if credentials are valid."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "202",
            description = "Successful login, returns JWT token.",
            content = @Content(schema = @Schema(implementation = LoginResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Invalid credentials or user not found.",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))
        )
    })
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(authHandler.login(request));
    }

}
