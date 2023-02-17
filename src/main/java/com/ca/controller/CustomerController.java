package com.ca.controller;

import com.ca.Apimessage.ApiMessage;
import com.ca.entity.Customer;
import com.ca.model.response.CustomerResponseDto;
import com.ca.model.response.UserResponseDto;
import com.ca.repository.CustomerRepository;
import com.ca.service.CustomerService;
import com.ca.utils.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody Customer customer) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true, customerService.createCustomer(customer), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping
    public ResponseEntity getAllCustomer(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true, customerService.getAllCustomer(pageNumber, pageSize), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity getCustomer(@PathVariable("customerId") Long customerId) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true, customerService.getCustomer(customerId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/{caId}")
    public ResponseEntity getCustomerOfCA(@RequestParam Long caId) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true, customerService.getCustomerOfCA(caId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @PutMapping("/{customerId}")
    public void UpdateCustomer(@RequestBody Customer customer, @PathVariable Long customerId){

        customerService.updateCustomer(customer, customerId);
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId){
        customerService.deleteCustomer(customerId);
    }
}
