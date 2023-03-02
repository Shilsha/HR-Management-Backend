package com.ca.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Getter
@Setter
@Builder
public class RequestResponse {

    private Long requestId;
    private String text;
    private Long caId;
    private String caName;
    private Long customerId;
    private String customerName;
    private Date createdDate;
    private Date modifiedDate;
    private Boolean isResolved;
    private Boolean requestStatus;
}
