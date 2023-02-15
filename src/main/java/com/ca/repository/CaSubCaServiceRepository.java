package com.ca.repository;

import com.ca.entity.CaSubCaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaSubCaServiceRepository extends JpaRepository<CaSubCaService, Long> {

    @Query(value = "select * from ca_sub_ca_service s where user_id=?1", nativeQuery = true)
    List<CaSubCaService> findByUserId(Long userId);

    @Query(value = "select * from ca_sub_ca_service s where user_id=?1", nativeQuery = true)
    Page<CaSubCaService> findByUserid(Long userId, Pageable pageable);



}
