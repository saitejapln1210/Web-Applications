package com.epam.controller;
import com.epam.clients.UserClient;
import com.epam.dto.UserDto;
import com.epam.dto.UserProfile;
import com.epam.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryUserController {
    @Autowired
    private UserClient userClient;

    @Autowired
    private LibraryService libraryService;
    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> getUser(){
        ResponseEntity<List<UserDto>> msg = userClient.getUser();
        return new ResponseEntity<>(msg.getBody(),msg.getStatusCode());
    }
    @GetMapping(value = "/users/{username}")
    public ResponseEntity<UserProfile> getUserByUserName(@PathVariable(value="username") String username){
        return new ResponseEntity<>(libraryService.getUserProfile(username), HttpStatus.OK);
    }
    @PostMapping(value = "/users")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto){
        ResponseEntity<UserDto> m = userClient.addUser(userDto);
        return new ResponseEntity<>(m.getBody(),m.getStatusCode());
    }
    @PutMapping(value = "/users/{username}")
    public ResponseEntity<String> updateUser(@PathVariable(value="username") String username,@RequestBody UserDto userDto){
        ResponseEntity<String> msg = userClient.updateUser(username,userDto);
        return new ResponseEntity<>(msg.getBody(),msg.getStatusCode());
    }
    @DeleteMapping(value = "/users/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value="username") String username){
        libraryService.deleteAllBook(username);
        ResponseEntity<String> msg = userClient.deleteUser(username);
        return new ResponseEntity<>(msg.getBody(),msg.getStatusCode());
    }
}
