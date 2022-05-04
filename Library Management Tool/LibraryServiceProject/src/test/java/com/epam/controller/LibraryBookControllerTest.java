package com.epam.controller;
import com.epam.clients.BookClient;
import com.epam.dto.BookDto;
import com.epam.exceptions.ServerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(LibraryBookController.class)
 class LibraryBookControllerTest {

    @MockBean
    BookClient bookClient;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @Test
    void getBook() throws Exception {

        BookDto bookDto=new BookDto(1,"Physics","epam","saiteja");
        List<BookDto> bookDtoList=new ArrayList<>();
        bookDtoList.add(bookDto);
        ResponseEntity<List<BookDto>> responseEntity=new ResponseEntity<>(bookDtoList, HttpStatus.OK);
        when(bookClient.getAllBooks()).thenReturn(responseEntity);
        mockMvc.perform(get("/library/books")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

    }

    @Test
    void getBookById() throws Exception {
        BookDto bookDto=new BookDto(1,"Physics","epam","saiteja");
        ResponseEntity<BookDto> responseEntity=new ResponseEntity<>(bookDto, HttpStatus.OK);
        when(bookClient.getBookById(anyInt())).thenReturn(responseEntity);
        mockMvc.perform(get("/library/books/{book-id}",1)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void addBook() throws Exception {
        BookDto bookDto=new BookDto(1,"Physics","epam","saiteja");
        ResponseEntity<BookDto> responseEntity=new ResponseEntity<>(bookDto, HttpStatus.CREATED);
        when(bookClient.addBook(bookDto)).thenReturn(responseEntity);
        mockMvc.perform(post("/library/books/")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(bookDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void updateBook() throws Exception {
        BookDto bookDto=new BookDto(1,"Physics","epam","saiteja");
        ResponseEntity<String> responseEntity=new ResponseEntity<>(bookDto.getBookName()+" book successfully updated", HttpStatus.ACCEPTED);
        when(bookClient.updateBook(1,bookDto)).thenReturn(responseEntity);
        mockMvc.perform(put("/library/books/{book-id}",1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(bookDto)))
                .andExpect(status().isAccepted())
                .andExpect(content().bytes("Physics book successfully updated".getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void deleteBook() throws Exception {
        ResponseEntity<String> msg=new ResponseEntity<>("Book Deleted Successfully",HttpStatus.OK);
        when(bookClient.deleteBook(anyInt())).thenReturn(msg);
        mockMvc.perform(delete("/library/books/{book-id}",1)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().bytes("Book Deleted Successfully".getBytes(StandardCharsets.UTF_8)));
    }

}
