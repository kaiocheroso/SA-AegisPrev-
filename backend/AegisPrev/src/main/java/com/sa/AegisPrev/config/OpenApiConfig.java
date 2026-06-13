package com.sa.AegisPrev.config;


import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My Spring Boot API")
                        .version("1.0.0")
                        .description("--- Aegis Prev ---" +
                                "\n API spring boot, O AegisPrev é uma solução tecnológica voltada para a área da " +
                                "saúde e prevenção. O objetivo do sistema é entregar uma plataforma segura, " +
                                "eficiente e robusta para auxiliar nos processos de prevenção e cuidados médicos."
                        )
                        .contact(new Contact()
                                .url("https://github.com/kaiocheroso/SA-AegisPrev-")
                        ));
    }
}
