package com.example.web.controllers;

import com.example.web.models.Book;
import com.example.web.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBook(@PathVariable UUID id) {
        return ResponseEntity.of(bookService.findBook(id));
    }

    @GetMapping("/")
    public List<Book> findAllBooks() {
        return bookService.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        return ResponseEntity.of(bookService.save(book));
    }

    @PatchMapping(value = "/{id}", consumes = "application/xml")
    public ResponseEntity<Book> modifyBook(@PathVariable UUID id, @RequestBody Book book) {
        book.setId(id);
        return ResponseEntity.of(bookService.modify(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable UUID id) {
        return ResponseEntity.of(bookService.delete(id));
    }

}
