package com.ca.model.response;

import com.ca.utils.Role;
import com.ca.utils.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubCAResponseDto {

    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String mobile;
    private String phone;
    private UserResponse userResponse;


}
