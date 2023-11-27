package com.example.web.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Author {
    private UUID id;
    private String name;
    private String surname;
}
