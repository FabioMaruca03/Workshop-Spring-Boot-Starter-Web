package com.example.web.controllers;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate booksApi() {
        return new RestTemplateBuilder()
                .rootUri("http://localhost:8080/books/")
                .defaultHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:1234".getBytes(UTF_8)))
                .build();
    }

}
