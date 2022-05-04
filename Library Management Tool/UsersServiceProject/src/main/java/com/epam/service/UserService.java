package com.epam.service;

import com.epam.dto.UserDto;
import com.epam.exceptions.EmailAlreadyExists;
import com.epam.exceptions.UserDoesNotExistException;
import com.epam.exceptions.UserNameAlreadyExists;

import java.util.List;


public interface UserService {

	void save(UserDto userDto) throws EmailAlreadyExists, UserNameAlreadyExists;
	UserDto findUserDtoByUserName(String username) throws UserDoesNotExistException;
	void deleteByUsername(String username) throws UserDoesNotExistException;
	List<UserDto> getAllUsers();
	UserDto updateUser(UserDto userDto) throws UserDoesNotExistException;
}
