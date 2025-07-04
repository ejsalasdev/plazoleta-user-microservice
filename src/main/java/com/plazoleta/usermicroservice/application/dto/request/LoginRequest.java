package com.plazoleta.usermicroservice.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to authenticate a user and obtain a JWT token")
public record LoginRequest(
        @Schema(
                description = "User's email address used for authentication. Must be a registered email in the system.",
                example = "admin.example@mail.com",
                format = "email",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String email,

        @Schema(
                description = "User's password for authentication. Will be validated against the encrypted password stored in the system.",
                example = "admin123",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String password
) {
}
