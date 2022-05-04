package com.epam.service;

import com.epam.dao.GroupRepository;
import com.epam.dao.UserRepository;
import com.epam.dto.UserDto;
import com.epam.entity.Group;
import com.epam.entity.User;
import com.epam.exceptions.UserAlreadyExsistException;
import com.epam.security.CustomUserDetails;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    UserRepository userRepository;
    @Mock
    GroupRepository groupRepository;
    @Mock
    ModelMapper modelMapper;
    @Test(expected =UserAlreadyExsistException.class )
    @DisplayName("checking user save")
    public void testRegisterUserException() throws UserAlreadyExsistException {
        UserDto userDto = new UserDto("saiteja","Sai@sivaram1210");
        List<User> userList = new ArrayList<>();
        User user = new User("saiteja","Sai@sivaram1210");
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        userService.register(userDto);
    }
    @Test()
    @DisplayName("checking user save")
    public void testRegisterUser() throws UserAlreadyExsistException {
        UserDto userDto = new UserDto("saiteja","Sai@sivaram1210");
        List<User> userList = new ArrayList<>();
        User user = new User("pavan","Sai@sivaram1210");
        userList.add(user);
        Group group = new Group("default");
        group.setUser(user);
        when(userRepository.findAll()).thenReturn(userList);
        when(modelMapper.map(userDto, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        assertTrue(true);
        when(groupRepository.save(group)).thenReturn(group);
        userService.register(userDto);


    }
    @Test
    @DisplayName("UserDao save user should save unique user")
    public void testIsUserExist() {
        UserDto userDto = new UserDto("saiteja","Sai@sivaram1210");
        List<User> userList = new ArrayList<>();
        User user = new User("saiteja","Sai@sivaram1210");
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        boolean result =userService.isUserExist(userDto);
        assertTrue(result);
    }
    @Test
    @DisplayName("User by UserDto")
    public void testUserByUserDto() {
        UserDto userDto = new UserDto("saiteja","Sai@sivaram1210");
        User user = new User("saiteja","Sai@sivaram1210");
        when(modelMapper.map(userDto, User.class)).thenReturn(user);
        User user1 = userService.getUserByDto(userDto);
        assertEquals(user1,user);
    }
    @Test
    @DisplayName("User by UserDto")
    public void testUserDtoByUser() {
        UserDto userDto = new UserDto("saiteja","Sai@sivaram1210");
        User user = new User("saiteja","Sai@sivaram1210");
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);
        UserDto userDto1 = userService.getDtoByUser(user);
        assertEquals(userDto1,userDto);
    }

    @Test
    public void testGetUserByName(){
        UserDto userDto = new UserDto("saiteja","Sai@sivaram1210");
        User user = new User("saiteja","Sai@sivaram1210");
        when(userRepository.findFirstByMasterUserName(userDto.getMasterUserName())).thenReturn(user);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);
        UserDto foundUserDto = userService.getUserByName(userDto);
        assertEquals(foundUserDto.getMasterUserName(),userDto.getMasterUserName());
    }

    @Test
    public void loadUserName(){
        User user = new User("saiteja","Sai@sivaram1210");
        UserDetails customUserDetails1= new CustomUserDetails(user);
        when(userRepository.findByMasterUserName(anyString())).thenReturn(user);
        UserDetails customUserDetails = userService.loadUserByUsername(user.getMasterUserName());
        assertEquals(customUserDetails.getUsername(),customUserDetails1.getUsername());
    }
    @Test
    public void testGetCurrentUser(){
        User user = new User("saiteja","Sai@sivaram1210");
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("saiteja");
        when(userRepository.findByMasterUserName("saiteja")).thenReturn(user);
        User user1= userService.getCurrentUser();
        assertEquals("saiteja",user1.getMasterUserName());
    }
}


