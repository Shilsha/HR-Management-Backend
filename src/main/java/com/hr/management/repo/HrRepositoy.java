package com.hr.management.repo;

import com.hr.management.entity.HrDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HrRepositoy extends JpaRepository<HrDetails ,Long> {

    @Query(value = "select * from hr_details v where email=?1 and password=?2",nativeQuery = true)
    HrDetails findByEmailAndPassword(String email, String password);
    Optional<HrDetails> findUserByEmail(String email);

    @Query(value="select * from hr_details c where email=?1",nativeQuery = true)
    HrDetails findByEmail(String email);

    @Query(value="select * from hr_details c where password=:?1",nativeQuery = true)
    HrDetails findByPassword(String password);
}
