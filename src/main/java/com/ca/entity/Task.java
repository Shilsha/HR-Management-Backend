package com.ca.entity;

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
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String duration;
    private String docUrl;
    private String docName;
    private String resolution;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date modifiedDate;
    private Boolean taskStatus;
    private Long assignedId;
    private Long assigneeId;
    private Long createdById;

}
