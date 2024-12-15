package com.example.movieservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI movieServiceAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Movie Service API")
                        .description("API dla serwisu filmowego z recenzjami")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Twoje Imię")
                                .email("twoj.email@example.com")));
    }
}