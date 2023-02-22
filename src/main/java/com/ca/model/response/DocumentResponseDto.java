package com.ca.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DocumentResponseDto {

    private Long docId;
    private String docName;
    private Long userId;
    private String docUrl;
    private Long serviceId;
    private String serviceName;

}
