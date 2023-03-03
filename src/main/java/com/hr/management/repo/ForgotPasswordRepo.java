package com.hr.management.repo;

import com.hr.management.entity.ForgotPasswordOTPDetails;
import com.hr.management.entity.HrDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ForgotPasswordRepo extends JpaRepository<ForgotPasswordOTPDetails,Long> {
    @Query(value="select * from forgot_passwordotpdetails c where emp_id=?1 and otp=?2",nativeQuery = true)
    ForgotPasswordOTPDetails findByEmpIdAAndOtp(Long empId,String otp);
}
