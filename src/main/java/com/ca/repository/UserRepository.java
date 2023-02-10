package com.ca.repository;

import com.ca.entity.User;
import com.ca.utils.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "select * from user u where email=?1 and password=?2",nativeQuery = true)
    User findByEmailAndPassword(String email, String password);

    @Query(value = "select * from user u where email =?1",nativeQuery = true)
    User findByEmail(String email);

    @Query(value = "select * from user u where role=?1 and first_name like CONCAT(?2,'%')", nativeQuery = true)
    List<User> findNameStartWith(int role, String name);
}
