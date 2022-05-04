package com.epam.service;

import com.epam.dao.GroupRepository;
import com.epam.dto.GroupDto;
import com.epam.entity.Group;
import com.epam.entity.User;
import com.epam.exceptions.GroupAlreadyExsistException;
import com.epam.exceptions.GroupNotFoundException;
import com.epam.exceptions.UserDoesNotExistException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class GroupServiceImpl implements  GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;
    private Group currentGroup;

    @Override
    public GroupDto addGroup(GroupDto groupDto) throws GroupAlreadyExsistException {
        boolean isGroupExist = isGroupExist(groupDto);
        if(isGroupExist){
            throw new GroupAlreadyExsistException("group already exist");
        }
        else{
            Group group = getGroupByDto(groupDto);
            Group persistedGroup = groupRepository.save(group);
            GroupDto groupDtoPersisted = getDtoByGroup(persistedGroup);
            currentGroup = persistedGroup;
            return groupDtoPersisted;
        }
    }
    @Override
    public Group getCurrentGroup(){
        return currentGroup;
    }

    @Override
    public void updateGroup(GroupDto groupDto) throws GroupNotFoundException {
        Group group = findGroupIdByUserId(groupDto.getId());
        group.setName(groupDto.getName());
        Group persistedGroup =groupRepository.save(group);
        currentGroup = persistedGroup;

    }
    @Override
    public void deleteGroup(int groupId) throws GroupNotFoundException {
        Group groupDeleted = findGroupIdByUserId(groupId);
        groupRepository.delete(groupDeleted);
    }
    @Override
    public List<Group> getAllGroups() throws UserDoesNotExistException {
        User user = userService.getCurrentUser();
        return groupRepository.getGroupsByUserId(user.getId());

    }
    @Override
    public GroupDto setUserToGroup(GroupDto groupDto){
        User user = userService.getCurrentUser();
        groupDto.setUser(user);
        return groupDto;
    }
    @Override
    public Group getGroupByDto(GroupDto groupDto){
        return modelMapper.map(groupDto,Group.class);

    }
    @Override
    public GroupDto getDtoByGroup(Group group){
        return modelMapper.map(group,GroupDto.class);
    }
    @Override
    public boolean isGroupExist(GroupDto groupDto) {
        User user = userService.getCurrentUser();
        List<Group> groupList= groupRepository.getGroupsByUserId(user.getId());
        boolean groupExist = false;
        for (Group iterGroup: groupList) {
            if(iterGroup.getName().equals(groupDto.getName())) {
                groupDto.setId(iterGroup.getId());
                groupExist = true;
                break;
            }
        }
        return groupExist;
    }
    @Override
    public Group findGroupIdByUserId(int groupId) throws GroupNotFoundException {
        User user = userService.getCurrentUser();
        Group group=groupRepository.findGroupIdByUserId(user.getId(),groupId);
        if(group!=null){
            return group;
        }
        throw new GroupNotFoundException("Group Not Found");
    }
}
