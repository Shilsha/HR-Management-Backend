package com.ca.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CaServiceResponseDto {

    private Long serviceId;
    private String serviceName;
    private String serviceDesc;
    private Long caId;

}
