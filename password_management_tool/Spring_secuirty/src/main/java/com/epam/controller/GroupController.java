package com.epam.controller;
import com.epam.dto.GroupDto;
import com.epam.entity.Group;
import com.epam.exceptions.GroupAlreadyExsistException;
import com.epam.exceptions.GroupNotFoundException;
import com.epam.exceptions.UserDoesNotExistException;
import com.epam.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class GroupController {
    @Autowired
    GroupService groupService;
    @GetMapping(value = "/groups/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable(value = "id") int groupId) throws GroupNotFoundException {
        Group group=groupService.findGroupIdByUserId(groupId);
        return new ResponseEntity<>(group,HttpStatus.OK);
    }
    @GetMapping("/groups")
    public ResponseEntity<List<Group>> getAllGroups() throws UserDoesNotExistException {
        List<Group> groupList= groupService.getAllGroups();
        return new ResponseEntity<>(groupList, HttpStatus.OK);
    }
    @PostMapping("/groups")
    public ResponseEntity<String> addGroup(@RequestBody @Valid GroupDto groupDto) throws GroupAlreadyExsistException, UserDoesNotExistException {
        GroupDto groupDtoWithUser = groupService.setUserToGroup(groupDto);
        GroupDto addedGroup = groupService.addGroup(groupDtoWithUser);
        return new ResponseEntity<>(addedGroup.getName()+" "+"successfully added", HttpStatus.CREATED);
    }
    @DeleteMapping("/groups/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable(value = "id") int groupId) throws GroupNotFoundException {
        groupService.deleteGroup(groupId);
        return new ResponseEntity<>("group deleted Successfully",HttpStatus.OK);
    }
    @PutMapping("/groups/{id}")
    public ResponseEntity<String> updateGroup(@PathVariable(value = "id") int groupId,@RequestBody @Valid GroupDto groupDto) throws GroupNotFoundException {
        groupDto.setId(groupId);
        groupService.updateGroup(groupDto);
        return new ResponseEntity<>("group updated Successfully",HttpStatus.OK);
    }
}
