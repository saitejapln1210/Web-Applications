package com.epam.controller;

import com.epam.dto.BookDto;
import com.epam.exceptions.BookNotFoundException;
import com.epam.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(BookController.class)
 class BookControllerTest {
    @MockBean
    BookService bookService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void getAllBooks() throws Exception {

        BookDto bookDto=new BookDto(1,"alchemist","Epam","saiteja");
        List<BookDto> bookDtoList=new ArrayList<>();
        bookDtoList.add(bookDto);
        when(bookService.getAllBooks()).thenReturn(bookDtoList);
        mockMvc.perform(get("/books/")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

    }

    @Test
    void getBookByID() throws Exception {

        BookDto bookDto=new BookDto(1,"Demo","epam","saiteja");

        when(bookService.getBookById(anyInt())).thenReturn(bookDto);

        mockMvc.perform(get("/books/{book-id}",1)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

    }

    @Test
    void getBookByIDThrowsException() throws Exception {

        BookDto bookDto=new BookDto(1,"Demo","epam","saiteja");

        when(bookService.getBookById(anyInt())).thenThrow(new BookNotFoundException("Book not found"));

        mockMvc.perform(get("/books/{book-id}",1))
                .andExpect(status().isNotFound());

    }

    @Test
    void addBook() throws Exception {

        BookDto bookDto=new BookDto(1,"Demo","epam","saiteja");

        when(bookService.addBook(bookDto)).thenReturn(bookDto);

        mockMvc.perform(post("/books/")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(bookDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

    }

    @Test
    void deleteBook() throws Exception {

        doNothing().when(bookService).deleteBook(anyInt());

        mockMvc.perform(delete("/books/{book-id}",1)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().bytes("Book Deleted Successfully".getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void updatedBook() throws Exception {

        BookDto bookDto=new BookDto(1,"Demo","epam","saiteja");

        when(bookService.updateBook(bookDto)).thenReturn(bookDto);

        mockMvc.perform(put("/books/{book-id}",1)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(bookDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

    }
    @Test
    void updatedBookException() throws Exception {

        BookDto bookDto=new BookDto(1,"","epam","saiteja");

        when(bookService.updateBook(bookDto)).thenReturn(bookDto);

        mockMvc.perform(put("/books/{book-id}",1)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(bookDto)))
                .andExpect(status().isBadRequest());


    }
}
