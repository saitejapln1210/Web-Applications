package com.epam.service;

import com.epam.dto.BookDto;
import com.epam.exceptions.BookNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

    BookDto addBook(BookDto bookDto);

    BookDto updateBook(BookDto bookDto) throws BookNotFoundException;

    void deleteBook(int id) throws BookNotFoundException;

    BookDto getBookById(int id) throws BookNotFoundException;

    List<BookDto> getAllBooks();
}
