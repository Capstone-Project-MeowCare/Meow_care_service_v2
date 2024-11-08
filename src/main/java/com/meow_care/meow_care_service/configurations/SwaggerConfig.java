package com.meow_care.meow_care_service.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
public class SwaggerConfig {


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Meow Care Service API")
                        .version("1.0")
                        .description("""
                                API documentation for Meow Care Service. This API allows users to manage pet care services, providing endpoints for creating, updating, deleting, and viewing service details.

                                Response Codes:
                                - **1000**: Success - The request was successful.
                                - **1001**: Created - The resource was successfully created.
                                - **1002**: Updated - The resource was successfully updated.
                                - **1003**: Deleted - The resource was successfully deleted.
                                - **2001**: Error - An internal server error occurred.
                                - **2002**: Not Found - The specified resource was not found.
                                - **2003**: Validation Error - The request contains invalid data.
                                - **2004**: Forbidden - Access to the resource is denied.
                                - **2005**: Token not valid - The provided token is invalid.
                                - **2006**: Unauthorized - Authentication is required.""")
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

    @PostConstruct
    @Profile("local")
    public void logSwaggerUrl() {
        log.info("Swagger UI available at: http://localhost:8080/api/swagger-ui.html");
        log.info("Swagger API docs available at: http://localhost:8080/api/v3/api-docs");
    }
}
