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

    @Operation(
        summary = "Create a new owner user",
        description = "Creates a new user with the OWNER role. Validates that document, email, and phone are unique. Returns the created user data.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201",
                description = "User created successfully",
                content = @io.swagger.v3.oas.annotations.media.Content(
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = SaveUserResponse.class)
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Invalid input data",
                content = @io.swagger.v3.oas.annotations.media.Content(
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.plazoleta.usermicroservice.infrastructure.exceptionhandler.ExceptionResponse.class)
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "409",
                description = "User with given document, email, or phone already exists",
                content = @io.swagger.v3.oas.annotations.media.Content(
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.plazoleta.usermicroservice.infrastructure.exceptionhandler.ExceptionResponse.class)
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Unexpected server error",
                content = @io.swagger.v3.oas.annotations.media.Content(
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = com.plazoleta.usermicroservice.infrastructure.exceptionhandler.ExceptionResponse.class)
                )
            )
        }
    )
    @PostMapping("/")
    public ResponseEntity<SaveUserResponse> save(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User data to create",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = SaveUserRequest.class)
            )
        )
        @RequestBody SaveUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userHandler.save(request));
    }
}
