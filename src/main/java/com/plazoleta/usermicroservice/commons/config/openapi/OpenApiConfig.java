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
                        **Microservicio de gestión de usuarios para la plataforma Plazoleta**
                        
                        Este microservicio maneja la autenticación, autorización y gestión de usuarios 
                        con roles ADMIN, OWNER, EMPLOYEE y CUSTOMER. Implementa arquitectura hexagonal y DDD.
                        
                        ## Características principales:
                        - ✅ Roles y permisos granulares  
                        - ✅ Validaciones de negocio robustas
                        - ✅ Arquitectura hexagonal + DDD
                        - ✅ Seguridad con Spring Security
                        
                        """,
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        description = "Desarrollo Local",
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
