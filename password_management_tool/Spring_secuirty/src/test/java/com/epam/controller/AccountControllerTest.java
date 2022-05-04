package com.epam.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import com.epam.dto.AccountDto;
import com.epam.dto.UserDto;
import com.epam.entity.Account;
import com.epam.exceptions.AccountAlreadyExistException;
import com.epam.exceptions.AccountNotFoundException;
import com.epam.security.JwtUtil;
import com.epam.service.AccountService;
import com.epam.service.GroupService;
import com.epam.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;



import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AccountControllerTest {
    @MockBean
    UserService userService;

    @MockBean
    AccountService accountService;

    @MockBean
    GroupService groupService;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    Authentication authentication;

    @MockBean
    SecurityContext securityContext;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
     void testGetAllAccounts() throws Exception {
        UserDto userDto=new UserDto("saiteja","Sai@sivaram1210");
        userDto.setId(1);
        authentication.setAuthenticated(true);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("saiteja");
        List<Account> accountList = new ArrayList<>();
        Account account = new Account("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        accountList.add(account);
        when(accountService.getAllAccounts(anyInt())).thenReturn(accountList);
        mockMvc.perform(get("/users/groups/{group-id}/accounts",1,2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
     void testAddAccount() throws Exception {
        AccountDto accountDto = new AccountDto("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        when(accountService.setGroupToAccount(accountDto,2)).thenReturn(accountDto);
        when(accountService.addAccount(accountDto)).thenReturn(accountDto);
        mockMvc.perform(post("/users/groups/{group-id}/accounts",2,3)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(accountDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=ISO-8859-1")));
    }
    @Test
     void testDeleteAccount() throws Exception{

        doNothing().when(accountService).deleteAccount(anyInt(),anyInt());
        mockMvc.perform(delete("/users/groups/{group-id}/accounts/{id}",1,2,3))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=ISO-8859-1")));
    }

    @Test
     void testUpdateAccount() throws Exception{

        AccountDto accountDto = new AccountDto("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        accountDto.setId(2);
        doNothing().when(accountService).updateAccount(accountDto,1);
        mockMvc.perform(put("/users/groups/{group-id}/accounts/{id}",1,2)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(accountDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=ISO-8859-1")));
    }

    @Test
    void getAccountById() throws Exception {

        Account account = new Account();
        when(accountService.findAccountIdGroupId(1,1)).thenReturn(account);
        mockMvc.perform(get("/users/groups/{group-id}/accounts/{id}",1,1)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
     void testDeleteAccountException() throws Exception{

        doThrow(new AccountNotFoundException("account not found")).when(accountService).deleteAccount(anyInt(),anyInt());
        mockMvc.perform(delete("/users/groups/{group-id}/accounts/{id}",1,2))
                .andExpect(status().isNotFound());
    }

    @Test
     void testAddAccountException() throws Exception {

        AccountDto accountDto = new AccountDto("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        when(accountService.setGroupToAccount(accountDto,2)).thenReturn(accountDto);
        when(accountService.addAccount(accountDto)).thenThrow(new AccountAlreadyExistException("account already exsist"));
        mockMvc.perform(post("/users/groups/{group-id}/accounts",2)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(accountDto)))
                .andExpect(status().isBadRequest());
    }
}