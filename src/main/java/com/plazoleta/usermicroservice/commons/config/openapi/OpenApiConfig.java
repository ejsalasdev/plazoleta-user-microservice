package com.plazoleta.usermicroservice.commons.config.openapi;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "👤 User Microservice API",
                version = "v1.0.0",
                description = """
                        **User management microservice for the Plazoleta platform**

                        This microservice handles authentication, authorization, and user management
                        with roles ADMIN, OWNER, EMPLOYEE, and CUSTOMER. Implements hexagonal architecture and DDD.

                        ## Main features:
                        - ✅ Granular roles and permissions
                        - ✅ Robust business validations
                        - ✅ Hexagonal architecture + DDD
                        - ✅ Security with Spring Security
                        """,
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        description = "Local Development",
                        url = "http://localhost:8091"
                )
        },
        security = @SecurityRequirement(name = "Bearer Authentication")
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {
}
