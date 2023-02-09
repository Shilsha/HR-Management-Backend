package com.ca.model.response;

import com.ca.utils.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
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

}
