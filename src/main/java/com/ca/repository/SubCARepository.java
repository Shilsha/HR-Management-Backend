package com.ca.repository;

import com.ca.entity.SubCA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCARepository extends JpaRepository<SubCA, Long> {

    @Query(value = "select * from subca s where ca_id=?1",nativeQuery = true)
    List<SubCA> findByCAId(Long caId);

    @Query(value = "select * from subca s where ca_id=?1",nativeQuery = true)
    Page<SubCA> findByCAid(Long caId, Pageable pageable);

    @Query(value = "select * from subca s where user_id=?1",nativeQuery = true)
    SubCA findByUserId(Long id);
}
