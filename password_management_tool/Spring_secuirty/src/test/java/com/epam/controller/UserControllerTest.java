package com.epam.controller;

import com.epam.authmodels.AuthenticationRequest;
import com.epam.controller.UserController;
import com.epam.dao.UserRepository;
import com.epam.dto.UserDto;
import com.epam.entity.User;

import com.epam.security.CustomUserDetails;
import com.epam.security.JwtUtil;
import com.epam.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith({MockitoExtension.class,SpringExtension.class})
@WebMvcTest(UserController.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserRepository userRepository;
    @MockBean
    UserService userService;
    @MockBean
    JwtUtil jwtUtil;
    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    Authentication authentication;
    @Autowired
    private ObjectMapper mapper;

    private UserDto userDto;
    private User user;

    @Test
    void registerUser() throws Exception {
        User user = new User("saiteja","Sai@sivaram1210");
        UserDto userDto = new UserDto("saiteja","Sai@sivaram1210");
        doNothing().when(userService).register(userDto);
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    void registerUserInvalid() throws Exception {
        UserDto userDto = new UserDto("saiteja","Sai@sivaram");
        doNothing().when(userService).register(userDto);
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
     void createAuthenticationToken() throws Exception {
        User user = new User("saiteja","Sai@sivaram");
        UserDto userDto = new UserDto("saiteja","Sai@sivaram");
        CustomUserDetails customUser=new CustomUserDetails(user);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(userService.loadUserByUsername(anyString())).thenReturn(customUser);
        String jwt="saiteja";
        when(jwtUtil.generateToken(any())).thenReturn(jwt);
        AuthenticationRequest authenticationRequest=new AuthenticationRequest("manikanta@gmail.com","Uma@1234");
        String json=mapper.writeValueAsString(authenticationRequest);
        mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON)
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}

