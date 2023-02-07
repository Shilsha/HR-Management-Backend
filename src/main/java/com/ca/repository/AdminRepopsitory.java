package com.ca.repository;

import com.ca.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepopsitory extends JpaRepository<Admin, Long> {

}
