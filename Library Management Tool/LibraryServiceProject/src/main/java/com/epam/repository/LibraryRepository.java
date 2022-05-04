package com.epam.repository;

import com.epam.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<Library,Integer> {

    List<Library> findAllByUsername(String username);

    @Transactional
    @Modifying
    @Query("DELETE from Library e where e.username =?1")
    void deleteAllBook(String username);

    @Transactional
    @Modifying
    @Query("DELETE from Library e where e.username =?1 and e.bookId=?2")
    void deleteBook(String username, int bookId);
}
