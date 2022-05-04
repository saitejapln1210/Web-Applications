package com.epam.controller;

import com.epam.authmodels.AuthenticationRequest;
import com.epam.authmodels.AuthenticationResponse;
import com.epam.dto.UserDto;
import com.epam.exceptions.UserAlreadyExsistException;
import com.epam.security.JwtUtil;
import com.epam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
   private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<UserDto> userRegister(@RequestBody @Valid UserDto userDto) throws UserAlreadyExsistException {
        userService.register(userDto);
        return new ResponseEntity<>(userService.getUserByName(userDto),HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws BadCredentialsException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        final UserDetails userDetails=userService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt=jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
