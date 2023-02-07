package com.ca.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AdminDto {
    private Long id;
    private Long userId;
    private String role;
}
