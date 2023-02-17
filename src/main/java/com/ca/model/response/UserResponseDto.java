package com.ca.model.response;

import com.ca.utils.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Builder
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String mobile;
    private String phone;
    private Role role;
    private String otp;
    private boolean otpVerify;
    private boolean status;
    private Date createdDate;
    private Date modifiedDate;
    private String profileUrl;
    private String profileName;
    private String gender;
    private String panCardNumber;

}
