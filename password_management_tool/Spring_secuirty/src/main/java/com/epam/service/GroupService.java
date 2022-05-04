package com.epam.service;

import com.epam.dto.GroupDto;
import com.epam.entity.Group;
import com.epam.exceptions.GroupAlreadyExsistException;
import com.epam.exceptions.GroupNotFoundException;
import com.epam.exceptions.UserDoesNotExistException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupService {
    GroupDto addGroup(GroupDto groupDto) throws GroupAlreadyExsistException;

    Group getCurrentGroup();

    void updateGroup(GroupDto groupDto) throws GroupNotFoundException;
    void deleteGroup(int groupId) throws GroupNotFoundException;
    List<Group> getAllGroups() throws UserDoesNotExistException;
    GroupDto setUserToGroup(GroupDto groupDto) throws UserDoesNotExistException;
    Group getGroupByDto(GroupDto groupDto);
    GroupDto getDtoByGroup(Group group);
    boolean isGroupExist(GroupDto groupDto);
    Group findGroupIdByUserId(int groupId) throws GroupNotFoundException;
}
