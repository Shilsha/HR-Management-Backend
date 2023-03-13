package com.hr.management.repo;

import com.hr.management.entity.EmployeeDetails;
import com.hr.management.entity.HrDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmpRepository extends JpaRepository<EmployeeDetails,Long> {
    @Query(value = "select * from employee_details v where email=?1 and password=?2",nativeQuery = true)
    EmployeeDetails findByEmailAndPassword(String email, String password);
    @Query(value="select * from employee_details c where email=?1",nativeQuery = true)
    EmployeeDetails findByEmail(String email);

    Optional<EmployeeDetails> findUserByEmail(String email);
    @Query(value = "select * from employee_details where id = ?1",nativeQuery = true)
    Optional<EmployeeDetails> findByEmpId(Long id);

//
}
