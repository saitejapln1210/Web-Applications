package com.epam.service;
import com.epam.dao.AccountRepository;
import com.epam.dto.AccountDto;
import com.epam.entity.Account;
import com.epam.entity.Group;
import com.epam.exceptions.AccountAlreadyExistException;
import com.epam.exceptions.AccountNotFoundException;
import com.epam.exceptions.GroupNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AccountDto addAccount(AccountDto accountDto) throws AccountAlreadyExistException {
        boolean isAccountExist = isAccountExist(accountDto);
        if(isAccountExist){
            throw new AccountAlreadyExistException("account already exist");
        }
        else{
            Account account = getAccountByDto(accountDto);
            Account persistedAccount = accountRepository.save(account);
            return  getDtoByAccount(persistedAccount);
        }
    }
    public AccountDto getDtoByAccount(Account persistedAccount) {
        return modelMapper.map(persistedAccount,AccountDto.class);

    }
    @Override
    public void updateAccount(AccountDto accountDto, int groupId) throws AccountNotFoundException, GroupNotFoundException {
       Group group = groupService.findGroupIdByUserId(groupId);
       Account account = findAccountIdGroupId(group.getId(),accountDto.getId());
       account.setUsername(accountDto.getUsername());
       account.setPassword(accountDto.getPassword());
       account.setUrl(accountDto.getUrl());
       accountRepository.save(account);
    }
    @Override
    public void deleteAccount(int accountId, int groupId) throws AccountNotFoundException, GroupNotFoundException {
        Group group = groupService.findGroupIdByUserId(groupId);
        Account account = findAccountIdGroupId(group.getId(),accountId);
        accountRepository.delete(account);
    }
    @Override
    public List<Account> getAllAccounts(int id) {
        return accountRepository.getAccountByGroupId(id);

    }
    @Override
    public Account getAccountByDto(AccountDto accountDto) {
        return modelMapper.map(accountDto,Account.class);

    }
    @Override
    public AccountDto setGroupToAccount(AccountDto accountDto,int groupId) throws GroupNotFoundException {
        Group group = groupService.findGroupIdByUserId(groupId);
        accountDto.setGroup(group);
        return accountDto;
    }
    @Override
    public boolean isAccountExist(AccountDto accountDto) {
        List<Account> accountList= accountRepository.getAccountByGroupId(accountDto.getGroup().getId());
        boolean accountExist = false;
        for (Account iterAccount: accountList) {
            if(iterAccount.getUsername().equals(accountDto.getUsername())) {
                accountDto.setId(iterAccount.getId());
                accountExist = true;
                break;
            }
        }
        return accountExist;
    }

    @Override
    public Account findAccountIdGroupId(int groupId,int accountId) throws AccountNotFoundException {
        Account account=accountRepository.findAccountIdGroupId(groupId,accountId);
        if(Objects.nonNull(account)){
            return account;
        }
        throw new AccountNotFoundException("account Not Found");
    }
}
