package com.example.bookmanager.Controller;

import com.example.bookmanager.Book;
import com.example.bookmanager.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // Get all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get a single book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Add a new book
    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    // Update an existing book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            Book bookToUpdate = existingBook.get();
            bookToUpdate.setTitle(updatedBook.getTitle());
            bookToUpdate.setAuthor(updatedBook.getAuthor());
            bookToUpdate.setPublishedYear(updatedBook.getPublishedYear());
            return ResponseEntity.ok(bookRepository.save(bookToUpdate));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Partially update a book (e.g., update only the title or author)
    @PatchMapping("/{id}")
    public ResponseEntity<Book> partialUpdateBook(@PathVariable Long id, @RequestBody Book partialBook) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            Book bookToUpdate = existingBook.get();
            if (partialBook.getTitle() != null) {
                bookToUpdate.setTitle(partialBook.getTitle());
            }
            if (partialBook.getAuthor() != null) {
                bookToUpdate.setAuthor(partialBook.getAuthor());
            }
            if (partialBook.getPublishedYear() != null) {
                bookToUpdate.setPublishedYear(partialBook.getPublishedYear());
            }
            return ResponseEntity.ok(bookRepository.save(bookToUpdate));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}