package com.plazoleta.usermicroservice.commons.config.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

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
                        - ✅ Autenticación JWT
                        - ✅ Roles y permisos granulares  
                        - ✅ Validaciones de negocio robustas
                        - ✅ Arquitectura hexagonal + DDD
                        - ✅ Seguridad con Spring Security
                        
                        ## Credenciales por defecto:
                        - **Admin**: admin@sistema.com / admin123
                        """,
                contact = @Contact(
                        name = "Equipo de Desarrollo Hogar360",
                        email = "dev@hogar360.com",
                        url = "https://github.com/hogar360"
                ),
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
