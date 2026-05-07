package com.example.tarifas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Tarifas")
                        .version("1.0")
                        .description("Documentação da API para gerenciamento de tabelas tarifárias e cálculos de consumo.")
                        .contact(new Contact()
                                .name("David Victor")
                                .email("davidvictorcontato7@gmail.com")));
    }
}