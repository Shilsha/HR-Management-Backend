package com.ca.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpVerifyRequest {

    private String otp;
    private String email;
}
