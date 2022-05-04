package com.epam.clients;

import com.epam.dto.BookDto;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="book")
@LoadBalancerClient(name="book")
public interface BookClient {
    @GetMapping("/books/{book-id}")
    ResponseEntity<BookDto> getBookById(@PathVariable(value="book-id") int bookId);
    @GetMapping("/books/")
    ResponseEntity<List<BookDto>> getAllBooks();
    @PostMapping("/books/")
    ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto);
    @PutMapping("/books/{book-id}")
    ResponseEntity<String> updateBook(@PathVariable(value = "book-id") int bookId,@RequestBody BookDto bookDto);
    @DeleteMapping("/books/{book-id}")
    ResponseEntity<String> deleteBook(@PathVariable(value="book-id") int bookId);
}
