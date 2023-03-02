package com.ca.model.response;

import com.ca.utils.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDto {

    private Long id;
    private Long userId;
    private Long caId;
    private String caName;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String mobile;
    private String phone;
    private String panCardNumber;
    private String aadhaarCardNumber;
    private String gender;
    private UserResponse userResponse;
    private Date createdDate;
    private Date modifiedDate;
    private String profileUrl;
    private String profileName;
}
