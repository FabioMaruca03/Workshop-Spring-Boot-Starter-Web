package com.example.web.security;

import com.example.web.models.Customer;
import com.example.web.repositories.CustomerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static com.example.web.models.ApplicationRole.ADMIN;
import static com.example.web.models.ApplicationRole.CUSTOMER;
import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {
    private final CustomerRepository customerRepository;

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity security) {
        return security
                .userDetailsService(customerRepository)
                .authorizeHttpRequests((registry) ->
                        registry
                                .requestMatchers("/login", "/logout", "/error").permitAll()
                                .anyRequest().authenticated()
                )
                .logout(configurer -> configurer
                        .logoutUrl("/logout")
                        .permitAll()
                )
                .httpBasic(withDefaults())
                .build();
    }

    @PostConstruct
    public void initAdminAccount() {
        customerRepository.save(
                Customer.builder()
                        .username("admin")
                        .password("1234")
                        .build()
        ).ifPresent(admin -> log.info("User {} persisted correctly!", admin.getUsername()));
    }

}
