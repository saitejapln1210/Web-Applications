package com.epam.controller;

import com.epam.clients.UserClient;
import com.epam.dto.BookDto;
import com.epam.dto.UserDto;
import com.epam.dto.UserProfile;
import com.epam.service.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(LibraryUserController.class)
 class LibraryUserControllerTest {
    @MockBean
    UserClient userClient;
    @MockBean
    LibraryService libraryService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Test
    void getUsers() throws Exception {
        UserDto userDto=new UserDto("saiteja","saiteja@gmail.com","Sai@sivaram1210");
        List<UserDto> userList=new ArrayList<>();
        userList.add(userDto);
        ResponseEntity<List<UserDto>> listResponseEntity=new ResponseEntity<>(userList, HttpStatus.OK);
        when(userClient.getUser()).thenReturn(listResponseEntity);
        mockMvc.perform(get("/library/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
    @Test
    void getUserByUserNameTest() throws Exception {
        BookDto bookDto=new BookDto(1,"alchemist","epam","Uma");
        List<BookDto> bookDtoList=new ArrayList<>();
        bookDtoList.add(bookDto);
        UserProfile userProfile= new UserProfile(bookDto.getBookName(),bookDtoList);
        when(libraryService.getUserProfile(anyString())).thenReturn(userProfile);
        mockMvc.perform(get("/library/users/{username}","saiteja")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
    @Test
    void addUserTest() throws Exception {
        UserDto userDto=new UserDto("saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        userDto.setId(1);
        ResponseEntity<UserDto> responseEntity=new ResponseEntity<>(userDto,HttpStatus.CREATED);
        when(userClient.addUser(userDto)).thenReturn(responseEntity);
        mockMvc.perform(post("/library/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

    }
    @Test
    void updateUser() throws Exception {
        UserDto userDto=new UserDto(1,"saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        userDto.setId(1);
        ResponseEntity<String> responseEntity=new ResponseEntity<>("saiteja1210 successfully updated",HttpStatus.OK);
        when(userClient.updateUser("saiteja1210",userDto)).thenReturn(responseEntity);
        mockMvc.perform(put("/library/users/{username}","saiteja1210")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().bytes("saiteja1210 successfully updated".getBytes(StandardCharsets.UTF_8)));
    }
    @Test
    void deleteUser() throws Exception {
        UserDto userDto=new UserDto("saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        userDto.setId(1);
        ResponseEntity<String> userResponseEntity=new ResponseEntity<>("User deleted successfully",HttpStatus.OK);
        doNothing().when(libraryService).deleteAllBook(anyString());
        when(userClient.deleteUser("saiteja1210")).thenReturn(userResponseEntity);
        mockMvc.perform(delete("/library/users/{username}","saiteja1210")
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().bytes("User deleted successfully".getBytes(StandardCharsets.UTF_8)));
    }
}
