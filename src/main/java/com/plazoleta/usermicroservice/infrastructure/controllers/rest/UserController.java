package com.plazoleta.usermicroservice.infrastructure.controllers.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plazoleta.usermicroservice.application.dto.request.SaveUserRequest;
import com.plazoleta.usermicroservice.application.dto.response.SaveUserResponse;
import com.plazoleta.usermicroservice.application.dto.response.UserInfoResponse;
import com.plazoleta.usermicroservice.application.handler.UserHandler;
import com.plazoleta.usermicroservice.infrastructure.exceptionhandler.ExceptionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "Operations related to users")
public class UserController {

    private final UserHandler userHandler;

    @Operation(summary = "Create a new owner user", description = "Creates a new user with the OWNER role. Validates that document, email, and phone are unique. Returns the created user data.", responses = {
            @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(schema = @Schema(implementation = SaveUserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "409", description = "User with given document, email, or phone already exists", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PostMapping("/")
    public ResponseEntity<SaveUserResponse> save(@RequestBody SaveUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userHandler.save(request));
    }

    @Operation(summary = "Get user by ID", description = "Returns user details (id, email, role) by user ID.", responses = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = UserInfoResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserInfoResponse> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userHandler.getUserById(id));
    }
}
