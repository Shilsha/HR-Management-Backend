package com.ca.model.request;

import com.ca.utils.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequestDto {

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String mobile;
    private String phone;
    private String password;
    private Role role;
    private String adminRole;
    private Long adminId;
    private Long caId;
    private Long addedBy;
    private String gender;
    private String panCardNumber;

}
