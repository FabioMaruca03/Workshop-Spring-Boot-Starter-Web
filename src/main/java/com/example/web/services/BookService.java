package com.example.web.services;

import com.example.web.models.Book;
import com.example.web.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;

    public Optional<Book> findBook(UUID id) {
        throw new BookNotFoundException("Could not find the ID");
        // return repository.findById(id);
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Optional<Book> delete(UUID id) {
        return repository.delete(id);
    }

    public Optional<Book> modify(Book book) {
        if (book.getId() == null)
            throw new ResponseStatusException(BAD_REQUEST, "Cannot find book in database");

        return repository.modify(book.getId(), book);
    }

    public Optional<Book> save(Book book) {
        return repository.save(book);
    }

    @ResponseStatus(BAD_REQUEST)
    public static class BookNotFoundException extends RuntimeException {
        public BookNotFoundException(String message) {
            super(message);
        }
    }

}
