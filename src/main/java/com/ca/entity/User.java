package com.ca.entity;

import com.ca.utils.Role;
import com.ca.utils.UserResponse;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String address;
    @Column(unique = true)
    private String mobile;
    private String phone;
    private String password;
    private Role role;
    private String otp;
    private boolean otpVerify;
    private boolean status;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date modifiedDate;
    private String profileUrl;
    private String profileName;
    private String gender;
    @Column(unique = true)
    private String panCardNumber;
    private UserResponse userResponse;
    @Column(unique = true)
    private String aadhaarCardNumber;

}
