package com.plazoleta.usermicroservice.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to create a new seller user in the system")
public record SaveUserRequest(
        @Schema(
                description = "User's first name. Must contain only letters and spaces.",
                example = "Juan Carlos",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String name,

        @Schema(
                description = "User's last name. Must contain only letters and spaces.",
                example = "Perez Garcia",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String lastName,

        @Schema(
                description = "User's document ID. Must be numeric only, between 8-10 digits. Must be unique in the system.",
                example = "12345678",
                minLength = 8,
                maxLength = 10,
                pattern = "^\\d+$",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String documentId,

        @Schema(
                description = "User's phone number. Can include '+' prefix for international format. Must be between 10-13 digits. Must be unique in the system.",
                example = "+573001234567",
                minLength = 10,
                maxLength = 13,
                pattern = "^\\+?\\d+$",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String phoneNumber,

        @Schema(
                description = "User's birth date. User must be at least 18 years old (calculated in America/Bogota timezone).",
                example = "1990-01-15",
                type = "string",
                format = "date",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String birthDate,

        @Schema(
                description = "User's email address. Must be a valid email format. Must be unique in the system.",
                example = "juan.perez@plazoleta.com",
                format = "email",
                maxLength = 50,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String email,

        @Schema(
                description = "User's password. Will be encrypted before storing. Minimum 8 characters recommended.",
                example = "mySecurePassword123",
                minLength = 8,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String password,
        @Schema(
                description = "User's role in the system. Must be 'OWNER' or 'EMPLOYEE'. 'OWNER' can create 'EMPLOYEE' users, 'EMPLOYEE' cannot create other users.",
                example = "OWNER",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String role
) {
}