package com.example.web.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

import static com.example.web.models.ApplicationRole.CUSTOMER;

@Data
@Builder
public class Customer implements UserDetails {
    private UUID id;
    private String password;
    private String username;

    @Builder.Default
    private boolean enabled = true;

    @Builder.Default
    private ApplicationRole role = CUSTOMER;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
