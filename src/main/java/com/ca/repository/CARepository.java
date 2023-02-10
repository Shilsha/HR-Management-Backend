package com.ca.repository;

import com.ca.entity.CA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CARepository extends JpaRepository<CA, Long> {

    @Query(value = "select * from ca c where user_id=?1",nativeQuery = true)
    CA findByUserId(Long id);
}
