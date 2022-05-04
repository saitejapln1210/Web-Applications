package com.epam.controller;
import com.epam.clients.BookClient;
import com.epam.dto.BookDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryBookController {
    @Autowired
    private BookClient bookClient;
    @Autowired
    private ModelMapper mapper;
    @GetMapping(value = "/books")
    public ResponseEntity<Object> getBook(){
        ResponseEntity<List<BookDto>> msg = bookClient.getAllBooks();
        return new ResponseEntity<>(msg.getBody(),msg.getStatusCode());
    }
    @GetMapping(value = "/books/{book-id}")
    public ResponseEntity<Object> getBookById(@PathVariable(value="book-id") int bookId){
        ResponseEntity<BookDto> msg = bookClient.getBookById(bookId);
        return new ResponseEntity<>(msg.getBody(),msg.getStatusCode());
    }
    @PostMapping(value = "/books")
    public ResponseEntity<Object> addBook(@RequestBody BookDto bookDto){
        ResponseEntity<BookDto> msg = bookClient.addBook(bookDto);
        return new ResponseEntity<>(msg.getBody(),msg.getStatusCode());
    }
    @PutMapping(value = "/books/{book-id}")
    public ResponseEntity<Object> updateBook(@PathVariable(value="book-id") int bookId,@RequestBody BookDto bookDto){
        ResponseEntity<String> msg = bookClient.updateBook(bookId,bookDto);
        return new ResponseEntity<>(msg.getBody(),msg.getStatusCode());
    }
    @DeleteMapping(value = "/books/{book-id}")
    public ResponseEntity<Object> deleteBook(@PathVariable(value="book-id") int bookId){
        ResponseEntity<String> msg = bookClient.deleteBook(bookId);
        return new ResponseEntity<>(msg.getBody(),msg.getStatusCode());
    }
}
