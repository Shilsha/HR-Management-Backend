package com.hr.management.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hr.management.entity.enums.EmpRole;
import com.hr.management.entity.enums.EmpType;
import com.hr.management.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Date;
@Data
@Valid
@AllArgsConstructor
//@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties
public class EmployeeReqDto {

    public Long id;
    public Long employeeId;
    public String firstName;
    public String lastName;
    public String email;
    public String designation;
    public String phoneNumber;
    public String totalExperience;
    @Enumerated(EnumType.STRING)
    public EmpRole empRole;
    @Enumerated(EnumType.STRING)
    public EmpType empType;
    @Enumerated(EnumType.STRING)
    public Status status;
    public String recruiterName;
    public String dob;
    public String currentAddress;

    public Date joiningDate;
}
