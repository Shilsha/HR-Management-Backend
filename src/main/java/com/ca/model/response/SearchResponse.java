package com.ca.model.response;

import com.ca.utils.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchResponse {

    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String phone;
    private String address;
    private Role role;
}
