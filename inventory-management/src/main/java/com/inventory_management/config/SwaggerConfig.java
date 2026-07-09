package com.inventory_management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI inventoryManagementAPI() {

        return new OpenAPI()

                .info(

                        new Info()

                                .title("Inventory Management System API")

                                .description("REST API Documentation for Inventory Management System built with Spring Boot and PostgreSQL")

                                .version("1.0.0")

                                .contact(

                                        new Contact()

                                                .name("MLUE TECHNOLOGY")

                                                .email("mluetechnologytz@gmail.com")

                                                .url("https://mluetechnology.me")

                                )

                                .license(

                                        new License()

                                                .name("MIT License")

                                                .url("https://opensource.org/licenses/MIT")

                                )

                );

    }

}