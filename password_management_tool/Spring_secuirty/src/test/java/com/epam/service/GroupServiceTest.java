package com.epam.service;

import com.epam.dao.GroupRepository;
import com.epam.dto.GroupDto;
import com.epam.entity.Group;
import com.epam.entity.User;
import com.epam.exceptions.GroupAlreadyExsistException;
import com.epam.exceptions.GroupNotFoundException;
import com.epam.exceptions.UserDoesNotExistException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class GroupServiceTest {

    @InjectMocks
    GroupServiceImpl groupService;
    @Mock
    UserService userService;
    @Mock
    GroupRepository groupRepository;
    @Mock
    ModelMapper modelMapper;
    @Test
    @DisplayName("test update Group")
    public void testUpdateGroup() throws GroupNotFoundException { // change
        GroupDto groupDto = new GroupDto("socialmedia");
        User user = new User();
        user.setId(1);
        groupDto.setId(user.getId());
        groupDto.setUser(user);
        Group group = new Group("socialmedia");
        group.setUser(user);
        when(userService.getCurrentUser()).thenReturn(user);
        when(groupRepository.findGroupIdByUserId(anyInt(),anyInt())).thenReturn(group);
        when(groupRepository.save(group)).thenReturn(group);
        groupService.updateGroup(groupDto);
        verify(groupRepository, times(1)).findGroupIdByUserId(anyInt(),anyInt());
    }
    @Test(expected = GroupNotFoundException.class)
    @DisplayName("test update Group")
    public void testUpdateGroupException() throws GroupNotFoundException { // change
        GroupDto groupDto = new GroupDto("government");
        User user = new User();
        user.setId(1);
        groupDto.setId(user.getId());
        groupDto.setUser(user);
        Group group = new Group("government");
        group.setUser(user);
        when(userService.getCurrentUser()).thenReturn(user);
        when(groupRepository.findGroupIdByUserId(user.getId(),group.getUser().getId())).thenReturn(null);
        when(groupRepository.save(group)).thenReturn(group);
        groupService.updateGroup(groupDto);
        verify(groupRepository, times(1)).findById(anyInt());
    }
    @Test
    @DisplayName("test delete Group")
    public void testDeleteGroup() throws GroupNotFoundException { // change
        GroupDto groupDto = new GroupDto("socialmedia");
        groupDto.setId(1);
        User user = new User();
        user.setId(1);
        groupDto.setUser(user);
        Group group = new Group("socialmedia");
        when(userService.getCurrentUser()).thenReturn(user);
        when(groupRepository.findGroupIdByUserId(anyInt(),anyInt())).thenReturn(group);
        groupService.deleteGroup(groupDto.getId());
        verify(groupRepository,times(1)).findGroupIdByUserId(anyInt(),anyInt());
    }
    @Test(expected = GroupNotFoundException.class)
    @DisplayName("test delete Group")
    public void testDeleteGroupException() throws GroupNotFoundException { // change
        GroupDto groupDto = new GroupDto("socialmedia");
        groupDto.setId(1);
        User user = new User();
        user.setId(1);
        groupDto.setUser(user);
        when(userService.getCurrentUser()).thenReturn(user);
        when(groupRepository.findGroupIdByUserId(anyInt(),anyInt())).thenReturn(null);
        groupService.deleteGroup(groupDto.getId());
        verify(groupRepository,times(1)).findGroupIdByUserId(anyInt(),anyInt());
    }
    @Test()
    @DisplayName("")
    public void testAddGroup() throws GroupAlreadyExsistException {
        GroupDto groupDto = new GroupDto("socialmedia");
        User user = new User();
        user.setId(2);
        groupDto.setUser(user);
        List<Group> groupList = new ArrayList<>();
        Group group = new Group("government");
        group.setUser(user);
        groupList.add(group);
        when(userService.getCurrentUser()).thenReturn(user);
        when(groupRepository.getGroupsByUserId(anyInt())).thenReturn(groupList);
        when(modelMapper.map(groupDto, Group.class)).thenReturn(group);
        when(modelMapper.map(group,GroupDto.class)).thenReturn(groupDto);
        when(groupRepository.save(group)).thenReturn(group);
        GroupDto groupDto1 =groupService.addGroup(groupDto);
        assertEquals(groupDto1.getName(),groupDto.getName());
        verify(groupRepository).save(group);
    }

    @Test(expected = GroupAlreadyExsistException.class)
    @DisplayName("")
    public void testAddGroupException() throws GroupAlreadyExsistException {
        GroupDto groupDto = new GroupDto("socialmedia");
        User user = new User();
        user.setId(2);
        groupDto.setUser(user);
        List<Group> groupList = new ArrayList<>();
        Group group = new Group("socialmedia");
        groupList.add(group);
        when(userService.getCurrentUser()).thenReturn(user);
        when(groupRepository.getGroupsByUserId(anyInt())).thenReturn(groupList);
        groupService.addGroup(groupDto);
        verify(groupService).isGroupExist(groupDto);
    }
    @Test
    @DisplayName("check Unique Group")
    public void testIsGroupExist(){
        GroupDto groupDto = new GroupDto("socialmedia");
        User user = new User();
        user.setId(1);
        groupDto.setUser(user);
        List<Group> groupList = new ArrayList<>();
        Group group = new Group("socialmedia");
        groupList.add(group);
        when(userService.getCurrentUser()).thenReturn(user);
        when(groupRepository.getGroupsByUserId(anyInt())).thenReturn(groupList);
        boolean result =groupService.isGroupExist(groupDto);
        assertTrue(result);
        verify(groupRepository).getGroupsByUserId(user.getId());
    }

    @Test
    @DisplayName("List All Groups")
    public void testGetAllGroups() throws UserDoesNotExistException {
        GroupDto groupDto = new GroupDto("socialmedia");
        Group group = new Group("socialmedia");
        List<Group> groupList = new ArrayList<>();
        groupList.add(group);
        User user = new User();
        user.setId(1);
        groupDto.setUser(user);
        when(userService.getCurrentUser()).thenReturn(user);
        when(groupRepository.getGroupsByUserId(user.getId())).thenReturn(groupList);
        assertEquals(1,groupService.getAllGroups().size());
    }
    @Test
    @DisplayName("")
    public void testGetCurrentGroup(){
        Group group = new Group("socialmedia");
        ReflectionTestUtils.setField(groupService,"currentGroup",group);
        Group group1 = groupService.getCurrentGroup();
        assertEquals(group1,group);
    }

    @Test
    @DisplayName("")
    public void testSetUserToGroup() {
        GroupDto groupDto = new GroupDto("socialmedia");
        User user = new User();
        user.setId(1);
        groupDto.setUser(user);
        when(userService.getCurrentUser()).thenReturn(user);
        GroupDto groupDto1 = groupService.setUserToGroup(groupDto);
        assertEquals(groupDto1.getName(),groupDto.getName());
    }
//    @Test
//    @DisplayName("")
//    public void testFindGroupById() throws GroupNotFoundException {
//        Optional<Group> optionalGroup= Optional.of(new Group("socialmedia"));
//        Group group =optionalGroup.get();
//        when(groupRepository.findGroupIdByUserId(anyInt(),anyInt())).thenReturn(group);
//        Group group1 = groupService.findGroupIdByUserId(anyInt());
//        assertEquals(group1.getName(),group.getName());
//    }

    @Test
    public void testFindGroupByUserId() throws GroupNotFoundException {
        User user = new User("saiteja","Sai@sivaram1210");
        Group group=new Group("socialmedia");
        group.setId(1);
        group.setUser(user);
        when(groupRepository.findGroupIdByUserId(anyInt(),anyInt())).thenReturn(group);
        when(userService.getCurrentUser()).thenReturn(user);
        assertEquals(groupService.findGroupIdByUserId(group.getId()).getName(),group.getName());
    }

}
