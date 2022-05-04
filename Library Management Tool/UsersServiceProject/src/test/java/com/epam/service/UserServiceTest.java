package com.epam.service;

import com.epam.dto.UserDto;
import com.epam.entity.User;
import com.epam.exceptions.EmailAlreadyExists;
import com.epam.exceptions.UserDoesNotExistException;
import com.epam.exceptions.UserNameAlreadyExists;
import com.epam.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    ModelMapper modelMapper;

    @Test
    public void findUserDtoByUserNameTest() throws UserDoesNotExistException {
        User user = new User("saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        UserDto userDto = new UserDto("saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        when(userRepository.findByUserName(user.getUserName())).thenReturn(user);
        when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);
        UserDto userDto1 = userService.findUserDtoByUserName(userDto.getUserName());
        assertEquals(userDto1.getUserName(),userDto.getUserName());
    }

    @Test(expected = EmailAlreadyExists.class)
    public void saveTestException1() throws UserNameAlreadyExists, EmailAlreadyExists {
        User user = new User("saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        UserDto userDto = new UserDto("saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(user);
        userService.save(userDto);
    }
    @Test(expected = UserNameAlreadyExists.class)
    public void saveTestException2() throws UserNameAlreadyExists, EmailAlreadyExists {
        User user = new User("saiteja1210","saiteja11@gmail.com","Sai@sivaram1210");
        UserDto userDto = new UserDto("saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        when(userRepository.findByEmail("saiteja111@gmail.com")).thenReturn(user);
        when(userRepository.findByUserName(userDto.getUserName())).thenReturn(user);
        userService.save(userDto);
    }
    @Test
    public void saveTest() throws UserNameAlreadyExists, EmailAlreadyExists {
        User user = new User("pavan","saiteja11@gmail.com","Sai@sivaram1210");
        UserDto userDto = new UserDto("saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        when(userRepository.findByEmail("saiteja111@gmail.com")).thenReturn(user);
        when(userRepository.findByUserName("saiteja")).thenReturn(user);
        when(modelMapper.map(userDto,User.class)).thenReturn(user);
        user.setId(1);
        when(userRepository.save(user)).thenReturn(user);
        userService.save(userDto);
        verify(userRepository).save(user);
    }
    @Test
    public void deleteUser() throws UserDoesNotExistException {
        User user = new User("pavan","saiteja11@gmail.com","Sai@sivaram1210");
        UserDto userDto = new UserDto("saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        when(userRepository.findByUserName(user.getUserName())).thenReturn(user);
        when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);
        when(modelMapper.map(userDto,User.class)).thenReturn(user);
        doNothing().when(userRepository).delete(user);
        userService.deleteByUsername("pavan");
        verify(userRepository).findByUserName("pavan");
    }
    @Test(expected = UserDoesNotExistException.class)
    public void deleteUserException() throws UserDoesNotExistException {
        User user = new User("pavan","saiteja11@gmail.com","Sai@sivaram1210");
        UserDto userDto = new UserDto("saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        when(userRepository.findByUserName(user.getUserName())).thenReturn(null);
        when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);
        when(modelMapper.map(userDto,User.class)).thenReturn(user);
        doNothing().when(userRepository).delete(user);
        userService.deleteByUsername("pavan");
    }
    @Test
    public void getAllUsersTest(){
        User user = new User("saiteja1210","saiteja11@gmail.com","Sai@sivaram1210");
        UserDto userDto = new UserDto("saiteja1210","saiteja@gmail.com","Sai@sivaram1210");
        List<User> userList=new ArrayList<>();
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);
        List<UserDto> userDtoList =userService.getAllUsers();
        verify(userRepository).findAll();
    }
    @Test
    public void updateUserDetails() throws UserDoesNotExistException {
        User user = new User();
        user.setUserName("pavan");
        user.setEmail("saiteja11@gmail.com");
        user.setPassword("Sai@sivaram1210");
        UserDto userDto = new UserDto("pavan","saiteja11@gmail.com","Sai@sivaram1210");
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(user));
        when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);
        when(modelMapper.map(userDto,User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);
        assertEquals(user.getUserName(),userService.updateUser(userDto).getUserName());
        assertEquals("pavan",user.getUserName());
        assertEquals("saiteja11@gmail.com",user.getEmail());
        assertEquals("Sai@sivaram1210",user.getPassword());
    }
}
