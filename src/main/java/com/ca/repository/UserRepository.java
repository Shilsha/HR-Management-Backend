package com.ca.repository;

import com.ca.entity.User;
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

    @Query(value = "select * from user u where first_name like CONCAT(:name,'%') and role=2", nativeQuery = true)
    List<User> findByName(String name);
}
