package com.ca.entity;

import com.ca.utils.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String address;

    private String mobile;
    private String phone;

    private String password;
    private Role role;

    private String otp;
    private boolean otpVerify;

    private boolean status;

}
