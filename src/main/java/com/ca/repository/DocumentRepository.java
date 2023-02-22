package com.ca.repository;

import com.ca.entity.Document;
import com.ca.model.response.DocumentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query(value = "select * from document d where customer_id=?1",nativeQuery = true)
    List<Document> findByCustomerId(Long customerId);

    @Query(value = "select * from document d where doc_name like CONCAT(?1, '%')", nativeQuery = true)
    Page<Document> findByName(String docName, Pageable pageable);

    @Query(value = "select * from document d where user_id=?1",nativeQuery = true)
    Page<Document> findByUserId(Long userId, Pageable pageable);

    @Query(value = "select * from document d where user_id=?1",nativeQuery = true)
    List<Document> findByUserid(Long userId);

    @Query(value = "select * from document d where doc_name like CONCAT(?1, '%')", nativeQuery = true)
    List<Document> findByname(String docName);
}
