package com.epam.service;
import com.epam.dao.GroupRepository;
import com.epam.dao.UserRepository;
import com.epam.dto.UserDto;
import com.epam.entity.Group;
import com.epam.entity.User;
import com.epam.exceptions.UserAlreadyExsistException;
import com.epam.security.CustomUserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public void register(UserDto userDto) throws UserAlreadyExsistException {
        boolean userExist = isUserExist(userDto);
        if(userExist){
            throw new UserAlreadyExsistException("user already exist");
        }
        else{
            userDto.setMasterPassword(passwordEncoder.encode(userDto.getMasterPassword()));
            User user = getUserByDto(userDto);
            User persistedUser = userRepository.save(user);
            Group group=new Group("Default");
            group.setUser(persistedUser);
            groupRepository.save(group);
        }
    }

    @Override
    public UserDto getUserByName(UserDto userDto) {
        User user = userRepository.findFirstByMasterUserName(userDto.getMasterUserName());
        return getDtoByUser(user);

    }
    @Override
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByMasterUserName(username);
    }
    public User getUserByDto(UserDto userDto){
        return modelMapper.map(userDto,User.class);
    }
    public UserDto getDtoByUser(User user){
        return modelMapper.map(user,UserDto.class);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= Optional.ofNullable(userRepository.findByMasterUserName(username))
                .orElseThrow(()->new UsernameNotFoundException("User with username: "+username+" not found."));
        return new CustomUserDetails(user);
    }
    @Override
    public boolean isUserExist(UserDto userDto) {
        List<User> userList= userRepository.findAll();
        boolean userExist = false;
        for (User iterUser: userList) {
            if(iterUser.getMasterUserName().equals(userDto.getMasterUserName())) {
                userDto.setId(iterUser.getId());
                userExist = true;
                break;
            }
        }
        return userExist;
    }


}
    
    
