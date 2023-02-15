package com.ca.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DocumentResponseDto {

    private Long id;
    private String docName;
    private Long userId;
    private String docUrl;
}
