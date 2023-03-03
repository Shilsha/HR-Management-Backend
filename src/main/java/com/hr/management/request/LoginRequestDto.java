package com.hr.management.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LoginRequestDto {

    public String password;
    public String email;
    public String empType;


}
