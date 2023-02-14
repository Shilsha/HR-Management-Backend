package com.ca.controller;

import com.ca.model.request.LoginRequest;
import com.ca.model.request.OtpVerifyRequest;
import com.ca.model.response.LoginResponse;
import com.ca.model.response.UserResponseDto;
import com.ca.repository.UserRepository;
import com.ca.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

    @Autowired
    private final LoginService service;
    @Autowired
    private final UserRepository userRepository;

    @PostMapping(value = "/login" , produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {

        System.out.println("Login controller");
        return ResponseEntity.ok(service.authenticate(loginRequest));

    }

    @PutMapping(value = "/verify")
    public ResponseEntity<UserResponseDto> verification(@RequestBody OtpVerifyRequest otpVerifyRequest){
        System.out.println("Verification process");
        return ResponseEntity.ok(service.verifyEmail(otpVerifyRequest));
    }
}
