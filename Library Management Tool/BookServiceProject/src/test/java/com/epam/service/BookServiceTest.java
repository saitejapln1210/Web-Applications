package com.epam.service;

import com.epam.dao.BookRepository;
import com.epam.dto.BookDto;
import com.epam.entity.Book;
import com.epam.exceptions.BookNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    ModelMapper modelMapper;

    @Mock
    BookRepository bookRepository;

    @Test
    void addBook() {
        BookDto bookDto=new BookDto(1,"alchemist","epam","saiteja");
        Book book=new Book();
        book.setBookId(1);
        book.setAuthor("saiteja");
        book.setBookPublisher("epam");
        book.setBookName("alchemist");
        when(modelMapper.map(bookDto,Book.class)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(modelMapper.map(book,BookDto.class)).thenReturn(bookDto);
        assertEquals(bookDto,bookService.addBook(bookDto));
        assertEquals("saiteja",book.getAuthor());
        assertEquals("epam",book.getBookPublisher());
        assertEquals("alchemist",book.getBookName());
    }

    @Test
    void updateBook() throws BookNotFoundException {
        BookDto bookDto=new BookDto(1,"alchemist","epam","saiteja");
        Book book=new Book(1,"alchemist","epam","saiteja");
        when(bookRepository.findById(anyInt())).thenReturn(java.util.Optional.of(book));
        when(modelMapper.map(book,BookDto.class)).thenReturn(bookDto);
        when(bookRepository.save(book)).thenReturn(book);
        when(modelMapper.map(book,BookDto.class)).thenReturn(bookDto);
        assertEquals(bookDto,bookService.updateBook(bookDto));
    }

    @Test
    void deleteBook() throws BookNotFoundException {
        BookDto bookDto=new BookDto(1,"alchemist","epam","saiteja");
        Book book=new Book(1,"alchemist","epam","saiteja");
        when(bookRepository.findById(anyInt())).thenReturn(java.util.Optional.of(book));
        doNothing().when(bookRepository).deleteById(1);
        bookService.deleteBook(1);
        verify(bookRepository,times(1)).deleteById(1);
    }

    @Test
    void getBookById() throws BookNotFoundException {
        BookDto bookDto=new BookDto(1,"alchemist","epam","saiteja");
        Book book=new Book(1,"alchemist","epam","saiteja");
        when(bookRepository.findById(anyInt())).thenReturn(java.util.Optional.of(book));
        when(modelMapper.map(book,BookDto.class)).thenReturn(bookDto);
        assertEquals(bookDto,bookService.getBookById(1));
    }

    @Test
    void getBookByIdInvalid() {
        BookDto bookDto=new BookDto(1,"alchemist","epam","saiteja");
        Book book=new Book(1,"alchemist","epam","saiteja");
        when(bookRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        assertThrows(BookNotFoundException.class,()->bookService.getBookById(1));
    }
    @Test
    void getAllBooks() {
        BookDto bookDto=new BookDto(1,"alchemist","epam","saiteja");
        Book book=new Book(1,"alchemist","epam","saiteja");
        when(modelMapper.map(book,BookDto.class)).thenReturn(bookDto);
        List<Book> bookEntities=new ArrayList<>();
        bookEntities.add(book);
        List<BookDto> bookDtoList=new ArrayList<>();
        bookDtoList.add(bookDto);
        when(bookRepository.findAll()).thenReturn(bookEntities);
        assertEquals(bookDtoList,bookService.getAllBooks());
    }
}