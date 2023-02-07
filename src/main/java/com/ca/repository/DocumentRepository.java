package com.ca.repository;

import com.ca.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query(value = "select * from document d where customer_id=?1",nativeQuery = true)
    List<Document> findByCustomerId(Long customerId);
}
