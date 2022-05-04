package com.epam.controller;
import com.epam.dto.BookDto;
import com.epam.exceptions.BookNotFoundException;
import com.epam.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping(value = "/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @GetMapping(value = "/")
    public ResponseEntity<List<BookDto>> getAllBooks(){
        List<BookDto> bookDtoList=bookService.getAllBooks();
        return new ResponseEntity<>(bookDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/{book-id}")
    public ResponseEntity<BookDto> getBookByID(@PathVariable(value = "book-id") int bookId) throws BookNotFoundException {
        BookDto bookDto= bookService.getBookById(bookId);
        return new ResponseEntity<>(bookDto,HttpStatus.OK);
    }
    @PostMapping(value = "/")
    public ResponseEntity<BookDto> addBook(@RequestBody @Valid BookDto bookDto){
        BookDto bookDtoAdded=bookService.addBook(bookDto);
        return new ResponseEntity<>(bookDtoAdded,HttpStatus.CREATED);
    }
    @DeleteMapping(value = "/{book-id}")
    public ResponseEntity<String> deleteBook(@PathVariable(value = "book-id") int bookId) throws BookNotFoundException {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>("Book Deleted Successfully",HttpStatus.OK);
    }
    @PutMapping(value = "/{book-id}")
    public ResponseEntity<String> updatedBook(@RequestBody @Valid BookDto bookDto,@PathVariable(value = "book-id") int bookId) throws BookNotFoundException {
        bookDto.setBookId(bookId);
        BookDto bookDtoToUpdate=bookService.updateBook(bookDto);
        return new ResponseEntity<>(bookDtoToUpdate.getBookName()+" book successfully updated",HttpStatus.ACCEPTED);
    }
}
