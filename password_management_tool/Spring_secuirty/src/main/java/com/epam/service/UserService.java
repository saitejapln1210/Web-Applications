package com.epam.service;

import com.epam.dto.UserDto;
import com.epam.entity.User;
import com.epam.exceptions.UserAlreadyExsistException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {




    User getUserByDto(UserDto userDto);

    boolean isUserExist(UserDto userDto) ;

    void register(UserDto userDto) throws UserAlreadyExsistException;

    UserDto getDtoByUser(User user);

    UserDto getUserByName(UserDto userDto);

    UserDetails loadUserByUsername(String userName);

    User getCurrentUser();


}
