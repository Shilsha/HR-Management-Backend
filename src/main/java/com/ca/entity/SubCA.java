package com.ca.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubCA {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long caId;
    private Long addedBy;
    private Long userId;
}
