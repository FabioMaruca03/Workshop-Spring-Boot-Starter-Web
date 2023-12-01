package com.example.web.controllers;

import com.example.web.models.Book;
import com.example.web.models.Customer;
import com.example.web.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('books:read')")
    public ResponseEntity<Book> findBook(@PathVariable UUID id) {
        return ResponseEntity.of(bookService.findBook(id));
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('books:read')")
    public List<Book> findAllBooks(@AuthenticationPrincipal Customer principal) {
        return bookService.findAll();
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('books:create')")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        return ResponseEntity.of(bookService.save(book));
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('books:modify')")
    public ResponseEntity<Book> modifyBook(@PathVariable UUID id, @RequestBody Book book) {
        book.setId(id);
        return ResponseEntity.of(bookService.modify(book));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('books:delete')")
    public ResponseEntity<Book> deleteBook(@PathVariable UUID id) {
        return ResponseEntity.of(bookService.delete(id));
    }

}
