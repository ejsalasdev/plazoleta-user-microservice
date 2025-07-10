package com.plazoleta.usermicroservice.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User information response containing user details and associated restaurant for employees")
public record UserInfoResponse(
        @Schema(description = "User's unique identifier", example = "1")
        Long id,
        
        @Schema(description = "User's email address", example = "juan.perez@plazoleta.com")
        String email,
        
        @Schema(description = "User's role in the system", example = "EMPLOYEE", allowableValues = {"ADMIN", "OWNER", "EMPLOYEE", "CUSTOMER"})
        String role,
        
        @Schema(description = "Restaurant ID for employees. Null for ADMIN, OWNER, and CUSTOMER roles", example = "123")
        Long restaurantId
) {
}
