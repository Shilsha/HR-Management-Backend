package com.ca.repository;

import com.ca.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaServiceRepository extends JpaRepository<Service, Long> {

    @Query(value = "select * from service s where user_id=?1 and service_status=1", nativeQuery = true)
    List<Service> findByUserId(Long userId);

    @Query(value = "select * from service s where service_status=1 and user_id=?1 group by service_name", nativeQuery = true)
    List<Service> findDistinctService(Long userId);

    @Query(value = "select * from service s where service_name=?1 and service_status=1 and user_id=?2", nativeQuery = true)
    List<Service> findSubServiceByServiceName(String serviceName, Long userId);
}
