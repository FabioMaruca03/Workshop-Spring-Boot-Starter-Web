package com.example.web.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Book {
    private UUID id;
    private String title;
    private Author author;
    private LocalDateTime release;
}
