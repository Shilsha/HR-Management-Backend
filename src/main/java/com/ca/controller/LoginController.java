package com.ca.controller;

import com.ca.model.request.LoginRequest;
import com.ca.model.response.LoginResponse;
import com.ca.repository.UserRepository;
import com.ca.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
//@RequestMapping("/login")
public class LoginController {

    @Autowired
    private final LoginService service;
    @Autowired
    private final UserRepository userRepository;

    @PostMapping(value = "/login" , produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {

        return ResponseEntity.ok(service.authenticate(loginRequest));

    }

    @PostMapping(value = "/verify" , produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity verification(@RequestParam(value = "email") String email, @RequestParam(value = "otp") String otp){
        System.out.println("Verification process");
        boolean verifiedResponse = service.verifyEmail(email,otp);
        if (verifiedResponse){
            return ResponseEntity.ok("OTP Verified");
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }

    }
}
