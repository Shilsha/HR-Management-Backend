package com.ca.repository;

import com.ca.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query(value = "select * from admin a where user_id=?1",nativeQuery = true)
    Admin findByUserId(Long id);
}
