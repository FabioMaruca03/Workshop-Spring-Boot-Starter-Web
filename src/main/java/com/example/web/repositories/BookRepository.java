package com.example.web.repositories;

import com.example.web.models.Book;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class BookRepository extends RegularRepository<Book, UUID> {

    @Override
    public Optional<Book> save(Book book) {
        do {
            book.setId(UUID.randomUUID());
        } while (findById(book.getId()).isPresent());

        return Optional.ofNullable(datasource.put(book.getId(), book))
                .or(() -> Optional.of(book));
    }
}
