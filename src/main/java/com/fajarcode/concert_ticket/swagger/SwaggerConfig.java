package com.fajarcode.concert_ticket.swagger;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api(){
        return new OpenAPI().info(new Info().version("Version 0.1").title("Concert-Ticket-App").contact(new Contact().name(": Fajar Abdillah Ahmad").email("fajardillah25@gmail.com")));
    }


}
