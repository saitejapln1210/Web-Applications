package com.epam.controller;
import com.epam.dto.AccountDto;
import com.epam.entity.Account;
import com.epam.exceptions.*;
import com.epam.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/users/groups/{group-id}")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @GetMapping(value="/accounts/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable(value="group-id") int groupId,@PathVariable(value="id") int accountId) throws AccountNotFoundException {
        Account account=accountService.findAccountIdGroupId(groupId,accountId);
        return new ResponseEntity<>(account,HttpStatus.OK);
    }
    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts(@PathVariable(value = "group-id") int groupId){
        List<Account> accountList= accountService.getAllAccounts(groupId);
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }
    @PostMapping("/accounts")
    public ResponseEntity<String> addAccount(@PathVariable(value = "group-id") int groupId,@RequestBody @Valid AccountDto accountDto) throws GroupNotFoundException, AccountAlreadyExistException {
        AccountDto accountDtoWithGroup = accountService.setGroupToAccount(accountDto,groupId);
        AccountDto addedAccount = accountService.addAccount(accountDtoWithGroup);
        return new ResponseEntity<>(addedAccount.getUsername()+" "+"account successfully added.", HttpStatus.CREATED);
    }
    @PutMapping("/accounts/{id}")
    public ResponseEntity<String> updateAccount(@PathVariable(value = "group-id") int groupId,@PathVariable(value="id") int accountId,@RequestBody @Valid AccountDto accountDto) throws AccountNotFoundException, GroupNotFoundException {
        accountDto.setId(accountId);
        accountService.updateAccount(accountDto,groupId);
        return new ResponseEntity<>("account successfully updated.", HttpStatus.OK);
    }
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable(value = "group-id") int groupId,@PathVariable(value="id") int accountId) throws AccountNotFoundException, GroupNotFoundException {
        accountService.deleteAccount(accountId,groupId);
        return new ResponseEntity<>("account deleted Successfully",HttpStatus.OK);
    }
}
