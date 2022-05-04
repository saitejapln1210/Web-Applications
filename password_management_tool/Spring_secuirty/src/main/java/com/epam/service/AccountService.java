package com.epam.service;

import com.epam.dto.AccountDto;
import com.epam.entity.Account;
import com.epam.exceptions.AccountAlreadyExistException;
import com.epam.exceptions.AccountNotFoundException;
import com.epam.exceptions.GroupNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {
    AccountDto addAccount(AccountDto accountDto) throws AccountAlreadyExistException;

   void updateAccount(AccountDto accountDto, int groupId) throws AccountNotFoundException, GroupNotFoundException;

    void deleteAccount(int accountId, int groupId) throws AccountNotFoundException, GroupNotFoundException;

    List<Account> getAllAccounts(int id);

    Account getAccountByDto(AccountDto accountDto);

    AccountDto getDtoByAccount(Account account);

    AccountDto setGroupToAccount(AccountDto accountDto, int groupId) throws GroupNotFoundException;
    boolean isAccountExist(AccountDto accountDto);
    Account findAccountIdGroupId(int groupId, int accountId) throws AccountNotFoundException;
}
