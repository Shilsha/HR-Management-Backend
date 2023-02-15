package com.ca.controller;

import com.ca.Apimessage.ApiMessage;
import com.ca.model.request.LoginRequest;
import com.ca.model.request.OtpVerifyRequest;
import com.ca.repository.UserRepository;
import com.ca.service.LoginService;
import com.ca.utils.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) throws Exception {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,service.authenticate(loginRequest), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);

    }

    @PutMapping(value = "/verify")
    public ResponseEntity verification(@RequestBody OtpVerifyRequest otpVerifyRequest) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,service.verifyEmail(otpVerifyRequest), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }
}
