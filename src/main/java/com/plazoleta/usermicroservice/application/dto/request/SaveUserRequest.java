package com.plazoleta.usermicroservice.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to create a new user in the system. Can be used for OWNER, EMPLOYEE, or CUSTOMER accounts depending on authentication context")
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
                description = "User's role in the system. For authenticated users: 'OWNER' or 'EMPLOYEE'. For public registration (clients): leave empty or null - will automatically be set to 'CUSTOMER'.",
                example = "OWNER",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String role
) {
}