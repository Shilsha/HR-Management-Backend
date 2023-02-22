package com.ca.model.response;

import com.ca.utils.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
        private String token;
        private Long id;
        private Long caUserId;
        private String firstName;
        private String lastName;
        private String email;
        private String address;
        private String phone;
        private String mobile;
        private Role role;
    }
