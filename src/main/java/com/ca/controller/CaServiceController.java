package com.ca.controller;

import com.ca.Apimessage.ApiMessage;
import com.ca.entity.Service;
import com.ca.service.CaServicesService;
import com.ca.utils.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CaServiceController {

    @Autowired
    private CaServicesService caServiceService;

    @GetMapping("/get_all_services")
    public ResponseEntity getAllService() throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,caServiceService.getAllServices(), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_service_by_userId")
    public ResponseEntity getService(@RequestParam Long userId) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,caServiceService.getService(userId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @PostMapping("/add_service")
    public ResponseEntity addService(@RequestBody Service service) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,caServiceService.addService(service), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_distinct_service_by_caId")
    public ResponseEntity getDistinctService(@RequestParam Long caId) throws JsonProcessingException {
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,caServiceService.getDistinctService(caId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_subService_by_serviceName")
    public ResponseEntity getSubServiceByServiceName(@RequestParam String serviceName, @RequestParam Long caId) throws JsonProcessingException {
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,caServiceService.getSubServiceByService(serviceName, caId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @DeleteMapping("/delete_service")
    public ResponseEntity deleteService(@RequestParam Long serviceId) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,caServiceService.deleteService(serviceId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

}
