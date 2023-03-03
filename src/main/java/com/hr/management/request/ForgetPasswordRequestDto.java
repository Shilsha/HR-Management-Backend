package com.hr.management.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ForgetPasswordRequestDto {

    public String password;
    public String email;
    public String confirmPassword;
    public String otp;
    public Long empId;
    public  String firstName;
    public String empType;

}
