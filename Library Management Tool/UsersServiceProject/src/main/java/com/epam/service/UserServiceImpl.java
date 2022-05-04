package com.epam.service;

import com.epam.dto.UserDto;
import com.epam.entity.User;
import com.epam.exceptions.EmailAlreadyExists;
import com.epam.exceptions.UserDoesNotExistException;
import com.epam.exceptions.UserNameAlreadyExists;
import com.epam.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
	@Override
	public void save(UserDto userDto) throws EmailAlreadyExists, UserNameAlreadyExists {
		if(isEmailExists(userDto.getEmail()))
			throw new EmailAlreadyExists("Email Already Exists");
		if(isUserNameExists(userDto.getUserName(),userDto))
			throw new UserNameAlreadyExists("UserName Already Exists");
		User user=getUserFromDto(userDto);
		userRepository.save(user);
	}
	private boolean isUserNameExists(String userName,UserDto userDto) {
		User user =  userRepository.findByUserName(userName);
		if(Objects.nonNull(user)){
			userDto.setId(user.getId());
			return true;
		}
		return false;
	}
	private boolean isEmailExists(String email) {
		User user =  userRepository.findByEmail(email);
		return Objects.nonNull(user);
	}
	public User getUserFromDto(UserDto userDto) {

		return modelMapper.map(userDto, User.class);
	}
	
	public UserDto getDtoFromUser(User user) {
		return modelMapper.map(user, UserDto.class);
	}
	@Override
	public UserDto findUserDtoByUserName(String userName) throws UserDoesNotExistException {
		User user= userRepository.findByUserName(userName);
		if(Objects.isNull(user)){
			throw new UserDoesNotExistException("no such user found");
		}
		return getDtoFromUser(user);
	}
	@Override
	public List<UserDto> getAllUsers(){
		List<User> userList = userRepository.findAll();
		return modelMapper.map(userList, new TypeToken<List<User>>(){}.getType());
	}
	@Override
	public void deleteByUsername(String username) throws UserDoesNotExistException {
		UserDto userDto = findUserDtoByUserName(username);
		userRepository.delete(getUserFromDto(userDto));
	}
	@Override
	public UserDto updateUser(UserDto userDto) throws UserDoesNotExistException {
		int userId = userDto.getId();
		User user = userRepository.findById(userId).orElseThrow(()->new UserDoesNotExistException("No such user found"));
		user.setPassword(userDto.getPassword());
		user.setEmail(userDto.getEmail());
		userRepository.save(user);
		return getDtoFromUser(user);
	}
}
