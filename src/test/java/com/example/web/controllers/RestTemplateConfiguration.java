package com.example.web.controllers;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate booksApi() {
        return new RestTemplateBuilder()
                .rootUri("http://localhost:8080/books/")
                .build();
    }

}
