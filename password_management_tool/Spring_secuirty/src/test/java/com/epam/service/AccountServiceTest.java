package com.epam.service;

import com.epam.dao.AccountRepository;
import com.epam.dao.GroupRepository;
import com.epam.dto.AccountDto;
import com.epam.dto.GroupDto;
import com.epam.entity.Account;
import com.epam.entity.Group;
import com.epam.exceptions.AccountAlreadyExistException;
import com.epam.exceptions.AccountNotFoundException;
import com.epam.exceptions.GroupNotFoundException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;
    @Mock
    GroupRepository groupRepository;
    @InjectMocks
    private AccountServiceImpl accountService;
    @Mock
    ModelMapper modelMapper;
    @Mock
    GroupService groupService;


    @Test
    @DisplayName("List All Accounts")
    public void testGetAllAccounts(){
        GroupDto groupDto = new GroupDto("socialmedia");
        groupDto.setId(1);
        List<Account> accountList = new ArrayList<>();
        Account account = new Account("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        Account account1 = new Account("instagram1235","Sai@sivaram1210","https://www.linkedin.com/feed/");
        Account account2 = new Account("instagram1234","Sai@sivaram1210","https://www.linkedin.com/feed/");
        accountList.add(account1);
        accountList.add(account2);
        accountList.add(account);
        when(accountRepository.getAccountByGroupId(anyInt())).thenReturn(accountList);
        assertEquals(3,accountService.getAllAccounts(anyInt()).size());
        verify(accountRepository).getAccountByGroupId(anyInt());
    }
    @Test
    @DisplayName("test delete Account")
    public void testDeleteAccount() throws AccountNotFoundException, GroupNotFoundException {
        AccountDto accountDto = new AccountDto("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        Account account = new Account("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        Group group = new Group("socialmedia");
        when(groupService.findGroupIdByUserId(anyInt())).thenReturn(group);
        when(accountRepository.findAccountIdGroupId(anyInt(),anyInt())).thenReturn(account);
        doNothing().when(accountRepository).delete(account);
        accountService.deleteAccount(3,1);
        verify(accountRepository).delete(account);
    }
    @Test(expected = AccountNotFoundException.class)
    @DisplayName("test delete Account")
    public void testDeleteAccountException() throws AccountNotFoundException, GroupNotFoundException {
        AccountDto accountDto = new AccountDto("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        Account account = new Account("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        Group group = new Group("socialmedia");
        when(groupService.findGroupIdByUserId(anyInt())).thenReturn(group);
        when(accountRepository.findAccountIdGroupId(anyInt(),anyInt())).thenReturn(null);
        doNothing().when(accountRepository).delete(account);
        accountService.deleteAccount(3,1);
        verify(accountRepository).delete(account);
    }
    @Test
    @DisplayName("test delete Account")
    public void testUpdateAccount() throws AccountNotFoundException, GroupNotFoundException {
        AccountDto accountDto = new AccountDto("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        Account account = new Account("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        Group group = new Group("socialmedia");
        when(groupService.findGroupIdByUserId(anyInt())).thenReturn(group);
        when(accountRepository.findAccountIdGroupId(anyInt(),anyInt())).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        accountService.updateAccount(accountDto,1);
        verify(accountRepository).save(account);
    }
    @Test(expected = AccountNotFoundException.class)
    @DisplayName("test delete Account")
    public void testUpdateAccountException() throws AccountNotFoundException, GroupNotFoundException {
        AccountDto accountDto = new AccountDto("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        Account account = new Account("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        Group group = new Group("socialmedia");
        when(groupService.findGroupIdByUserId(anyInt())).thenReturn(group);
        when(accountRepository.findAccountIdGroupId(anyInt(),anyInt())).thenReturn(null);
        when(accountRepository.save(account)).thenReturn(account);
        accountService.updateAccount(accountDto,1);
        verify(accountRepository).save(account);
    }
    @Test(expected = AccountAlreadyExistException.class)
    @DisplayName("test add account")
    public void testAddAccountException() throws AccountAlreadyExistException {
        AccountDto accountDto = new AccountDto("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        Account account = new Account("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        Group group = new Group("socialmedia");
        group.setId(1);
        account.setGroup(group);
        accountDto.setGroup(group);
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
        when(accountRepository.getAccountByGroupId(anyInt())).thenReturn(accountList);
        when(modelMapper.map(accountDto,Account.class)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(modelMapper.map(account,AccountDto.class)).thenReturn(accountDto);
        AccountDto accountDto1 = accountService.addAccount(accountDto);
        verify(accountRepository).save(account);
        assertEquals(accountDto1.getUsername(),accountDto.getUsername());
    }
    @Test
    @DisplayName("test add account")
    public void testAddAccount() throws AccountAlreadyExistException {
        AccountDto accountDto = new AccountDto("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        Account account = new Account("instagram1234","Sai@sivaram1210","https://www.linkedin.com/feed/");
        Group group = new Group("socialmedia");
        group.setId(1);
        account.setGroup(group);
        accountDto.setGroup(group);
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
        when(accountRepository.getAccountByGroupId(anyInt())).thenReturn(accountList);
        when(modelMapper.map(accountDto,Account.class)).thenReturn(account);
        when(modelMapper.map(account,AccountDto.class)).thenReturn(accountDto);
        when(accountRepository.save(account)).thenReturn(account);
        AccountDto accountDto1 = accountService.addAccount(accountDto);
        verify(accountRepository).save(account);
        assertEquals(accountDto1.getUsername(),accountDto.getUsername());
    }

    @Test
    public void getAccountByDto(){
        AccountDto accountDto = new AccountDto("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        Account account = new Account("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        when(modelMapper.map(accountDto,Account.class)).thenReturn(account);
        Account account1 = accountService.getAccountByDto(accountDto);
        assertEquals(account1,account);
    }

    @Test
    public void testSetGroupToAccount() throws GroupNotFoundException {
        AccountDto accountDto = new AccountDto("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        Group group = new Group();
        group.setId(1);
        accountDto.setGroup(group);
        when(groupService.findGroupIdByUserId(anyInt())).thenReturn(group);
        AccountDto accountDto1 = accountService.setGroupToAccount(accountDto,1);
        assertEquals(accountDto1.getUsername(),accountDto.getUsername());
    }
    @Test(expected = GroupNotFoundException.class)
    public void testSetGroupToAccountException() throws GroupNotFoundException {
        AccountDto accountDto = new AccountDto("instagram123","Sai@sivaram1210","https://www.linkedin.com/feed/");
        Group group = null;
        when(groupService.findGroupIdByUserId(anyInt())).thenThrow(GroupNotFoundException.class);
        accountService.setGroupToAccount(accountDto,1);
    }
}
