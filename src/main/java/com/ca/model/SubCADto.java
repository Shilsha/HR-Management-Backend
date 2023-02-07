package com.ca.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class SubCADto {
    private Long id;
    private Long caId;
    private Long addedBy;
    private Long userId;
}
