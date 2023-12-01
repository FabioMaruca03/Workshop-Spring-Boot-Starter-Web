package com.example.web.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationRole {
    CUSTOMER("books:read", "books:buy"),
    ADMIN("books:create", "books:read", "books:modify"),
    DEVELOPER("books:*");

    private final Set<String> authorities;

    ApplicationRole(String... auths) {
        authorities = Set.of(auths);
    }

    public Set<GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

}
