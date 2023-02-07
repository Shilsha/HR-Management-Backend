package com.ca.repository;

import com.ca.entity.CA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CARepository extends JpaRepository<CA, Long> {

}
