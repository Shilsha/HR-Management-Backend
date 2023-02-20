package com.ca.repository;

import com.ca.entity.Customer;
import com.ca.entity.User;
import com.ca.utils.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "select * from user u where email=?1 and password=?2",nativeQuery = true)
    User findByEmailAndPassword(String email, String password);

    @Query(value = "select * from user u where email =?1",nativeQuery = true)
    User findByEmail(String email);

    @Query(value = "select * from user u where role=?1 and first_name like CONCAT(?2,'%')", nativeQuery = true)
    Page<User> findNameStartWith(int role, String name, Pageable pageable);

    @Query(value = "select * from user u where role=?1 and first_name like CONCAT(?2,'%')", nativeQuery = true)
    List<User> findNameStartwith(int role, String name);

    @Query(value = "select * from user u where role=?1", nativeQuery = true)
    List<User> findByRole(int role);

    @Query(value = "select * from user u where role=?1", nativeQuery = true)
    Page<User> findByrole(int role,Pageable pageable);

    @Query(value = "select * from user u where id=?1 and role=?2", nativeQuery = true)
    Optional<User> findByUserId(Long userId, int role);
}
