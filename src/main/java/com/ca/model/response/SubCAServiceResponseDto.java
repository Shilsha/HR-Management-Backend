package com.ca.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SubCAServiceResponseDto {

    private Long serviceId;
    private Long subCaId;
    private String serviceName;
    private String serviceDesc;
}
