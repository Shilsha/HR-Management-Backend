package com.hr.management.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hr.management.entity.enums.EmpRole;
import com.hr.management.entity.enums.EmpType;
import com.hr.management.entity.enums.Status;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "EmployeeDetails")
public class EmployeeDetails extends BaseEntity{

    @Column(name = "emp_id")
    public Long employeeId;
    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    @Column(name="email",unique = true)
    public String email;

    @Column(name = "designation")
    public String designation;
    @Column(name = "password")
    public String password;

    @Column(name = "experience")
    public String experience;

    @Column(name = "emp_role")
    @Enumerated(EnumType.STRING)
    public EmpRole empRole;

    @Column(name = "emp_type")
    @Enumerated(EnumType.STRING)
    public EmpType empType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public Status status;
    @Column(name = "joining_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    public Date joiningDate;
    @PrePersist
    protected void prePersist() {
        if (getCreatedAt() == null) setCreatedAt(LocalDateTime.now());
        if (getUpdatedAt() == null) setUpdatedAt(LocalDateTime.now());


    }
}
