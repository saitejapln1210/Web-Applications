package com.epam.dao;

import com.epam.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query(
            "select a from Account a where a.id=?2" +
                    " and a.group.id = ?1"
    )
    Account findAccountIdGroupId(int groupId, int accountId);

    @Query(value = "select a from Account a where a.group.id=?1")
    List<Account> getAccountByGroupId(int id);

    Account findAccountByUsername(String username);
}
