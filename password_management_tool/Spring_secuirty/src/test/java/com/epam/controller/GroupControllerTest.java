package com.epam.controller;

import com.epam.dto.GroupDto;
import com.epam.entity.Group;
import com.epam.entity.User;
import com.epam.exceptions.UserDoesNotExistException;
import com.epam.security.JwtUtil;
import com.epam.service.AccountService;
import com.epam.service.GroupService;
import com.epam.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class GroupControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
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

    @Test
     void testGetAllGroupsException() throws Exception {
        List<Group> groupList = new ArrayList<>();
        Group group = new Group("socialmedia");
        groupList.add(group);
        when(groupService.getAllGroups()).thenThrow(new UserDoesNotExistException("user does not exisit"));
        mockMvc.perform(get("/users/groups"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "saiteja",password = "Sai@sivaram1210")
    void testGetAllGroups() throws Exception {
        List<Group> groupList = new ArrayList<>();
        Group group = new Group("socialmedia");
        groupList.add(group);
        when(groupService.getAllGroups()).thenReturn(groupList);
        mockMvc.perform(get("/users/groups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
    @Test
     void testAddGroup() throws Exception {
        GroupDto groupDto = new GroupDto("socialmedia");
        User user = new User();
        user.setId(1);
        when(groupService.setUserToGroup(groupDto)).thenReturn(groupDto);
        when(groupService.addGroup(groupDto)).thenReturn(groupDto);
        mockMvc.perform(post("/users/groups",user.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(groupDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=ISO-8859-1")));
    }
    @Test
    void testAddGroupException() throws Exception {
        GroupDto groupDto = new GroupDto("");
        User user = new User();
        user.setId(1);
        when(groupService.setUserToGroup(groupDto)).thenReturn(groupDto);
        when(groupService.addGroup(groupDto)).thenReturn(groupDto);
        mockMvc.perform(post("/users/groups",user.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(groupDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
     void testDeleteGroup() throws Exception{
        doNothing().when(groupService).deleteGroup(anyInt());
        mockMvc.perform(delete("/users/groups/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=ISO-8859-1")));
    }
    @Test
     void testUpdateGroup() throws Exception{
        GroupDto groupDto = new GroupDto("socialmedia");
        groupDto.setId(1);
        doNothing().when(groupService).updateGroup(groupDto);
        mockMvc.perform(put("/users/groups/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(groupDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=ISO-8859-1")));
    }
    @Test
    void getGroupById() throws Exception {
        Group group = new Group("socialmedia");
        when(groupService.findGroupIdByUserId(1)).thenReturn(group);
        mockMvc.perform(get("/users/groups/{group-id}",1)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
    @Test
     void testUpdateGroupException() throws Exception{
        GroupDto groupDto = new GroupDto("");
        groupDto.setId(1);
        mockMvc.perform(put("/users/groups/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(groupDto)))
                .andExpect(status().isBadRequest());
    }
}