package com.epam.controller;

import com.epam.dto.LibraryDto;
import com.epam.exceptions.ServerException;
import com.epam.exceptions.UserBookLimitReachedException;
import com.epam.exceptions.UserHasAlreadyBookException;
import com.epam.service.LibraryService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(LibraryController.class)
 class LibraryControllerTest {

    @MockBean
    LibraryService libraryService;
    @Autowired
    MockMvc mockMvc;
    @Test
    void issueBook() throws Exception {
        LibraryDto libraryDto=new LibraryDto(1,"saiteja",1);
        when(libraryService.issueBook(anyString(),anyInt())).thenReturn(libraryDto);
        mockMvc.perform(post("/library/users/{username}/books/{book-id}","saiteja",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void deleteBook() throws Exception {
        doNothing().when(libraryService).deleteBook(anyString(),anyInt());
        mockMvc.perform(delete("/library/users/{username}/books/{book-id}","saiteja",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/json")));
    }
    @Test
    void issueBookUserLimitException() throws Exception {
        LibraryDto libraryDto=new LibraryDto(1,"saiteja",1);
        when(libraryService.issueBook(anyString(),anyInt())).thenThrow(new UserBookLimitReachedException("book limit reached"));
        mockMvc.perform(post("/library/users/{username}/books/{book-id}","saiteja",1))
                .andExpect(status().isBadRequest());
    }
    @Test
    void issueBookUserAlreadyHasBook() throws Exception {
        LibraryDto libraryDto=new LibraryDto();
        libraryDto.setUsername("saiteja");
        libraryDto.setId(1);
        libraryDto.setBookId(1);
        when(libraryService.issueBook(anyString(),anyInt())).thenThrow(new UserHasAlreadyBookException("user already has book"));
        mockMvc.perform(post("/library/users/{username}/books/{book-id}","saiteja",1))
                .andExpect(status().isBadRequest());
    }

    @Test
    void issueBookUserFeignException() throws Exception {
        LibraryDto libraryDto=new LibraryDto(1,"saiteja",1);
        when(libraryService.issueBook(anyString(),anyInt())).thenThrow(new ServerException(404,"feign exception"));
        mockMvc.perform(post("/library/users/{username}/books/{book-id}","saiteja",1))
                .andExpect(status().isNotFound());
    }


}
