package com.hr.management.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@JsonInclude(value = JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include. NON_NULL)
public class LoginResponseDto {

    public Long employeeId;


    public String firstName;
    public String lastName;

    public String email;
    public String mobile;
    public String token;

    public String message;


    public LoginResponseDto(Long employeeId, String firstName) {
        this.employeeId = employeeId;
        this.firstName = firstName;
    }

    // public UserType userType;
}
