package com.epam.dao;

import com.epam.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GroupRepository extends JpaRepository<Group,Integer> {
    @Query(
            "select g from Group g where g.id=?2" +
                    " and g.user.id = ?1"
    )
    Group findGroupIdByUserId(int userId, int groupId);

    @Query(value = "select g from Group g where g.user.id=?1")
    List<Group> getGroupsByUserId(int id);
}
