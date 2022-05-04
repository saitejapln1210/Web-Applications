package com.epam.service;

import com.epam.dao.BookRepository;
import com.epam.dto.BookDto;
import com.epam.entity.Book;
import com.epam.exceptions.BookNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ModelMapper modelMapper;
    private static final String BOOK_NOT_FOUND = "book not found";
    @Override
    public BookDto addBook(BookDto bookDto) {
        Book book =modelMapper.map(bookDto, Book.class);
        bookRepository.save(book);
        return modelMapper.map(book,BookDto.class);
    }
    @Override
    public BookDto updateBook(BookDto bookDto) throws BookNotFoundException {
        Book book =bookRepository.findById(bookDto.getBookId()).orElseThrow(()->new BookNotFoundException(BOOK_NOT_FOUND));
        book.setBookName(bookDto.getBookName());
        book.setBookPublisher(bookDto.getBookPublisher());
        book.setAuthor(bookDto.getAuthor());
        bookRepository.save(book);
        return modelMapper.map(book,BookDto.class);
    }
    @Override
    public void deleteBook(int id) throws BookNotFoundException {
        Book book =bookRepository.findById(id).orElseThrow(()->new BookNotFoundException(BOOK_NOT_FOUND));
        bookRepository.deleteById(book.getBookId());
    }
    @Override
    public BookDto getBookById(int id) throws BookNotFoundException {
        Book book =bookRepository.findById(id).orElseThrow(()->new BookNotFoundException(BOOK_NOT_FOUND));
        return modelMapper.map(book,BookDto.class);
    }
    @Override
    public List<BookDto> getAllBooks() {
        List<Book> bookEntities=bookRepository.findAll();
        List<BookDto> bookDtoList=new ArrayList<>();
        for (Book book:bookEntities) {
            bookDtoList.add(modelMapper.map(book,BookDto.class));
        }
        return bookDtoList;
    }
}
