package com.ca.repository;

import com.ca.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query(value = "select * from request r where ca_id=?1 and request_status=1 order by id desc", nativeQuery = true)
    List<Request> findByCAId(Long caId);
    @Query(value = "select * from request r where ca_id=?1 and request_status=1 order by id desc", nativeQuery = true)
    Page<Request> findByCAid(Long caId, Pageable pageable);
    @Query(value = "select * from request r where customer_id=?1 and request_status=1 order by id desc", nativeQuery = true)
    List<Request> findByCustomerId(Long customerId);

    @Query(value = "select * from request r where customer_id=?1 and request_status=1 order by id desc", nativeQuery = true)
    Page<Request> findByCustomerid(Long customerId, Pageable pageable);
}
