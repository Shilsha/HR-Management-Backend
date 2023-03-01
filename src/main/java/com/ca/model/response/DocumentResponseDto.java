package com.ca.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Getter
@Setter
@Builder
public class DocumentResponseDto {

    private Long docId;
    private String docName;
    private Long userId;
    private String docUrl;
    private Long serviceId;
    private Date createdDate;
    private Date modifiedDate;
    private String serviceName;
    private String subService;

}
