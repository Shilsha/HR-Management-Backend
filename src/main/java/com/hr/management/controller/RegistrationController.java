package com.hr.management.controller;

import com.hr.management.request.EmployeeReqDto;
import com.hr.management.request.LoginRequestDto;
import com.hr.management.service.EmpRegistrationService;
import com.hr.management.service.LoginService;
import com.hr.management.utils.ApiMessage;
import com.hr.management.utils.ApiResponse;
import com.hr.management.utils.BadReqException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.regex.Pattern;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RegistrationController {

    @Autowired
    EmpRegistrationService empRegistrationService;

    @PostMapping(value = "/register_emp")
    public ResponseEntity registerEmp(@RequestBody EmployeeReqDto employeeReqDto)
            throws Exception {
        ApiResponse apiResponse = empRegistrationService.registor(employeeReqDto);
        return apiResponse.getResponse(apiResponse);
    }
    @PostMapping(value = "/update_emp")
    public ResponseEntity updateEmp(@RequestBody EmployeeReqDto employeeReqDto)
            throws Exception {
        ApiResponse apiResponse = empRegistrationService.registor(employeeReqDto);
        return apiResponse.getResponse(apiResponse);
    }
    @PostMapping(value = "/list_emp")
    public ResponseEntity listEmp()
            throws Exception {
        ApiResponse apiResponse = empRegistrationService.listofEmp();
        return apiResponse.getResponse(apiResponse);
    }
}
