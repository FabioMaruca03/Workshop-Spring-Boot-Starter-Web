package com.example.web.repositories;

import com.example.web.models.Customer;
import com.example.web.security.PasswordEncoderConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Import(PasswordEncoderConfiguration.class)
public class CustomerRepository extends RegularRepository<Customer, UUID> implements UserDetailsService {
    private final PasswordEncoder encoder;

    @Override
    public Optional<Customer> save(Customer element) {
        if (datasource.values().stream().anyMatch(x -> x.getUsername().equalsIgnoreCase(element.getUsername())))
            return Optional.empty();

        element.setPassword(encoder.encode(element.getPassword()));
        do {
            element.setId(UUID.randomUUID());
        } while (findById(element.getId()).isPresent());

        return Optional.ofNullable(datasource.put(element.getId(), element))
                .or(() -> Optional.of(element));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return datasource.values().stream()
                .filter(x -> x.getUsername().equalsIgnoreCase(username))
                .findAny()
                .orElseThrow(() -> new UsernameNotFoundException("User with username %s does not exist in the database".formatted(username)));
    }

}
