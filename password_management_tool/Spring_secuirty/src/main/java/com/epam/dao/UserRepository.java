package com.epam.dao;
import com.epam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByMasterUserName(String username);

    User findFirstByMasterUserName(String name);
}
