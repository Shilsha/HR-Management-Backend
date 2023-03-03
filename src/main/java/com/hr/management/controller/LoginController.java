package com.hr.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hr.management.entity.ForgotPasswordOTPDetails;
import com.hr.management.request.ForgetPasswordRequestDto;
import com.hr.management.request.LoginRequestDto;
import com.hr.management.service.LoginService;
import com.hr.management.utils.ApiMessage;
import com.hr.management.utils.ApiResponse;
import com.hr.management.utils.BadReqException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

    @Autowired
    LoginService hrLoginService;

    @GetMapping("/hello")
    public String hello() {
        return  "hello";
    }
    @PostMapping(value = "/hr/login")
    public ResponseEntity hrLogin(@RequestBody LoginRequestDto loginRequestDto) throws Exception {

        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        boolean emailValidation = Pattern.compile(regexPattern)
                .matcher(loginRequestDto.getEmail())
                .matches();
        System.out.println("Email Validate: " + emailValidation);
            if (emailValidation==true || emailValidation==false) {

                ApiResponse apiResponse = new ApiResponse(HttpStatus.OK, true, hrLoginService.hrlogin(loginRequestDto), ApiMessage.Api_Message);
                return apiResponse.getResponse(apiResponse);
            } else {
                throw new BadReqException(ApiMessage.Enter_VALID_EMAIL);
            }
           }

           //___________________________employee Login api _____________________//
           @PostMapping(value = "/emp/login")
           public ResponseEntity empLogin(@RequestBody LoginRequestDto loginRequestDto)
                   throws Exception {
               ApiResponse apiResponse = new ApiResponse(HttpStatus.OK, true, hrLoginService.emplogin(loginRequestDto), ApiMessage.Api_Message);
               return apiResponse.getResponse(apiResponse);
           }






  /*  @PostMapping(value = "/emp/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity empLogin(@RequestBody LoginRequestDto loginRequestDto)

            throws Exception {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        boolean emailValidation = Pattern.compile(regexPattern)
                .matcher(loginRequestDto.getEmail())
                .matches();
        System.out.println("Email Validate: " + emailValidation);
        if (emailValidation) {

            ApiResponse apiResponse = new ApiResponse(HttpStatus.OK, true, hrLoginService.empLogin(loginRequestDto), ApiMessage.Api_Message);
            return apiResponse.getResponse(apiResponse);
        } else {
            throw new BadReqException(ApiMessage.Enter_VALID_EMAIL);
        }

    }*/

    @PostMapping(value = "/forget-password/emailveriy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity hrForgetPasswordEmailVerify(@RequestBody LoginRequestDto loginRequestDto) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        boolean emailValidation = Pattern.compile(regexPattern)
                .matcher(loginRequestDto.getEmail())
                .matches();
        System.out.println("Email Validate: " + emailValidation);
        try{
        if (emailValidation) {

            ApiResponse apiResponse = new ApiResponse(HttpStatus.OK, true, hrLoginService.forgetPasswordEmailVerify(loginRequestDto), ApiMessage.Api_Message);
            return apiResponse.getResponse(apiResponse);
        }
        else {
            throw new BadReqException(ApiMessage.Enter_VALID_EMAIL);
        }
        }
        catch (Exception e)
        {
            throw new BadReqException(ApiMessage.INTERNAL_ERROR);
        }

    }

    @PostMapping(value = "/forget-password/otpverify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity hrForgetPasswordOtpVerify(@RequestBody ForgetPasswordRequestDto forgetPasswordRequestDto)

            throws Exception {

            ApiResponse apiResponse = new ApiResponse(HttpStatus.OK, true, hrLoginService.forgetPasswordOtpVerify(forgetPasswordRequestDto), ApiMessage.Api_Message);
            return apiResponse.getResponse(apiResponse);
    }
    @PostMapping(value = "/forget-password/create-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity hrForgetCreatePassword(@RequestBody LoginRequestDto loginRequestDto)

            throws Exception {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        boolean emailValidation = Pattern.compile(regexPattern)
                .matcher(loginRequestDto.getEmail())
                .matches();
        System.out.println("Email Validate: " + emailValidation);
        try{
        if (emailValidation) {

            ApiResponse apiResponse = new ApiResponse(HttpStatus.OK, true, hrLoginService.hrForgetCreatePassword(loginRequestDto), ApiMessage.Api_Message);
            return apiResponse.getResponse(apiResponse);
        }
        else {
            throw new BadReqException(ApiMessage.Enter_VALID_EMAIL);
        }
        }
        catch (Exception e)
        {
            throw new BadReqException(ApiMessage.INTERNAL_ERROR);
        }
    }

}
