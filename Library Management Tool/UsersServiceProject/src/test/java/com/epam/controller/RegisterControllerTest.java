package com.epam.controller;

import com.epam.dto.UserDto;
import com.epam.entity.User;
import com.epam.exceptions.EmailAlreadyExists;
import com.epam.exceptions.UserDoesNotExistException;
import com.epam.exceptions.UserNameAlreadyExists;
import com.epam.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(RegisterController.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class RegisterControllerTest {
    @MockBean
    private UserService userService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Test
    void addUser() throws Exception {
        User user = new User("saiteja1210","saoteja@gmail.com","Sai@sivaram1210");
        UserDto userDto = new UserDto("saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        user.setId(1);
        userDto.setId(1);
        doNothing().when(userService).save(userDto);
        when(userService.findUserDtoByUserName(userDto.getUserName())).thenReturn(userDto);
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void getAllUsers() throws Exception {
        User user = new User("saiteja1210","saoteja@gmail.com","Sai@sivaram1210");
        UserDto userDto = new UserDto("saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        user.setId(1);
        userDto.setId(1);
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto);
        when(userService.getAllUsers()).thenReturn(userDtoList);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void deleteUser() throws Exception {
        User user = new User("saiteja1210","saoteja@gmail.com","Sai@sivaram1210");
        UserDto userDto = new UserDto("saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        user.setId(1);
        doNothing().when(userService).deleteByUsername(userDto.getUserName());
        mockMvc.perform(delete("/users/{username}",userDto.getUserName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")));
    }
    @Test
    void getUser() throws Exception {
        User user = new User("saiteja1210","saoteja@gmail.com","Sai@sivaram1210");
        UserDto userDto = new UserDto("saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        user.setId(1);
        userDto.setId(1);
        when(userService.findUserDtoByUserName(userDto.getUserName())).thenReturn(userDto);
        mockMvc.perform(get("/users/{username}",userDto.getUserName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
    @Test
    void updateUserTest() throws Exception {
        User user = new User("saiteja1210","saoteja@gmail.com","Sai@sivaram1210");
        UserDto userDto = new UserDto("saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        user.setId(1);
        when(userService.findUserDtoByUserName(userDto.getUserName())).thenReturn(userDto);
        userDto.setId(1);
        when(userService.updateUser(userDto)).thenReturn(userDto);
        mockMvc.perform(put("/users/{username}",userDto.getUserName())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/json")));
    }

    @Test
    void addUserException() throws Exception {
        User user = new User("saiteja1210","","Sai@sivaram1210");
        UserDto userDto = new UserDto("saiteja1210","","Sai@sivaram1210");
        user.setId(1);
        userDto.setId(1);
        doNothing().when(userService).save(userDto);
        when(userService.findUserDtoByUserName(userDto.getUserName())).thenReturn(userDto);
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void addUserException1() throws Exception {
        User user = new User(1,"saiteja1210","","Sai@sivaram1210");
        UserDto userDto = new UserDto(1,"saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        user.setId(1);
        userDto.setId(1);
        doThrow(new UserNameAlreadyExists("user already exist")).when(userService).save(userDto);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void addUserException2() throws Exception {
        User user = new User(1,"saiteja1210","","Sai@sivaram1210");
        UserDto userDto = new UserDto(1,"saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        user.setId(1);
        userDto.setId(1);
        doThrow(new EmailAlreadyExists("email already exist")).when(userService).save(userDto);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void addUserException3() throws Exception {
        User user = new User("saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        UserDto userDto = new UserDto("saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        user.setId(1);
        userDto.setId(1);
        doNothing().when(userService).save(userDto);
        when(userService.findUserDtoByUserName(userDto.getUserName())).thenThrow(new UserDoesNotExistException("user not found"));
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(userDto)))
                .andExpect(status().isNotFound());
    }
}
