package com.epam.controller;

import com.epam.dto.UserDto;
import com.epam.exceptions.EmailAlreadyExists;
import com.epam.exceptions.UserDoesNotExistException;
import com.epam.exceptions.UserNameAlreadyExists;
import com.epam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class RegisterController {
    @Autowired
    UserService userService;
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }
    @PostMapping("/users")
    public ResponseEntity<UserDto> addUser(@RequestBody @Valid UserDto userDto) throws UserNameAlreadyExists, EmailAlreadyExists, UserDoesNotExistException {
        userService.save(userDto);
        return new ResponseEntity<>(userService.findUserDtoByUserName(userDto.getUserName()),HttpStatus.CREATED);
    }
    @DeleteMapping("/users/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable("username") String username) throws UserDoesNotExistException {
        userService.deleteByUsername(username);
        return new ResponseEntity<>("User deleted successful",HttpStatus.OK);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable("username") String userName) throws UserDoesNotExistException {
       return new ResponseEntity<>(userService.findUserDtoByUserName(userName),HttpStatus.OK);
    }

    @PutMapping("/users/{username}")
    public ResponseEntity<String> updateUser(@PathVariable("username") String username, @RequestBody @Valid UserDto userDto) throws UserDoesNotExistException {
        UserDto getUserDto = userService.findUserDtoByUserName(username);
        userDto.setId(getUserDto.getId());
        userService.updateUser(userDto);
        return new ResponseEntity<>(username+" "+"successfully updated", HttpStatus.OK);
    }
}
