package com.hr.management.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table()
public class ForgotPasswordOTPDetails extends BaseEntity{

    @Column(name = "emp_id")
    public Long employeeId;
    @Column(name = "otp")
    public String otp;
    @Column(name = "isOtpVerified")
    public boolean otpVerified;


    @PrePersist
    protected void prePersist() {
        if (getCreatedAt() == null) setCreatedAt(LocalDateTime.now());
        if (getUpdatedAt() == null) setUpdatedAt(LocalDateTime.now());


    }

}
