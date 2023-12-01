package com.example.web.controllers;

import com.example.web.models.Author;
import com.example.web.models.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@Import(RestTemplateConfiguration.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
class BookControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void shouldConvert() {
        Book.BookBuilder builder = Book.builder()
                .id(UUID.randomUUID())
                .title("Test")
                .author(Author.builder()
                        .name("Fabio")
                        .surname("Maruca")
                        .build())
                .release(LocalDateTime.now());

        System.out.println(objectMapper.writeValueAsString(builder.build()));
    }

    @Test
    void shouldCreateBook() throws Exception {
        Book.BookBuilder builder = Book.builder()
                .id(UUID.randomUUID())
                .title("Test")
                .author(Author.builder()
                        .name("Fabio")
                        .surname("Maruca")
                        .build())
                .release(LocalDateTime.now());


        MvcResult result = mockMvc.perform(post("/books/")
                        .header("Content-Type", "application/json")
                        .content(objectMapper.writeValueAsString(builder.build()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test"))
                .andReturn();
    }

    @Test
    @SuppressWarnings("all")
    void shouldReturnAllBooks() {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = new TestingAuthenticationToken("admin", "1234", "ROLE_CUSTOMER");
        context.setAuthentication(authentication);

        ResponseEntity<List> response = restTemplate.getForEntity("/", List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());

        Objects.requireNonNull(response.getBody()).forEach(System.out::println);
    }

}
