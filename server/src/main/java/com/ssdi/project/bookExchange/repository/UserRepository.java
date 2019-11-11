package com.ssdi.project.bookExchange.repository;

import com.ssdi.project.bookExchange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Query("select u from User u where name like %?1%")
    List<User> findByName(String name);

    @Query("select u from User u where id = ?1")
    User getById(int userId);

    @Query("select u from User u where name like %?1% or last_name like %?1% or email like %?1% or email like %?1%")
    List<User> searchUsers(String query);

}
