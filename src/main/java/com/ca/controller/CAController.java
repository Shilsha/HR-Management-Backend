package com.ca.controller;

import com.ca.Apimessage.ApiMessage;
import com.ca.entity.CA;
import com.ca.model.response.CaServiceResponseDto;
import com.ca.model.response.UserResponseDto;
import com.ca.service.CAService;
import com.ca.utils.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ca")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CAController {

    @Autowired
    private CAService caService;

    @PostMapping("/create_CA")
    public ResponseEntity createCA(@RequestBody CA ca) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,caService.createCA(ca), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_all_CA")
    public ResponseEntity getAllCA(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,caService.getAllCA(pageNumber, pageSize), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_CA_By_userId")
    public ResponseEntity getCAById(@RequestParam("userId") Long id) throws JsonProcessingException {
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,caService.getCAById(id), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_services_By_caId")
    public ResponseEntity getService(@RequestParam Long caId) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,caService.getService(caId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @PutMapping("/assign_customer")
    public ResponseEntity assignCustomer(@RequestParam Long customerId, @RequestParam Long caId) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,caService.assignCustomer(customerId, caId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @PutMapping("/update_CA")
    public void updateCA(@RequestBody CA ca){

        caService.updateCA(ca);
    }

    @DeleteMapping("/delete_CA")
    public void deleteCA(@RequestParam Long caId){
        caService.deleteCA(caId);
    }
}
