package com.hr.management.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hr.management.entity.EmployeeDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
//@JsonInclude(value = JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include. NON_NULL)
public class ResponseDto {
    public Long employeeId;
    public String firstName;
    public String lastName;

    public String email;
    public String mobile;
    public String token;

    public String message;
 List<EmployeeDetails> employeeDetailsList;
     public EmployeeDetails employeeDetails;
    public ResponseDto(Long employeeId, String firstName) {
        this.employeeId = employeeId;
        this.firstName = firstName;
    }
}
