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

    @PostMapping("/create_customer")
    public ResponseEntity create(@RequestBody Customer customer) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true, customerService.createCustomer(customer), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_all_customer")
    public ResponseEntity getAllCustomer(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true, customerService.getAllCustomer(pageNumber, pageSize), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @Deprecated
    @GetMapping("/{customerId}")
    public ResponseEntity getCustomer(@PathVariable("customerId") Long customerId) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true, customerService.getCustomer(customerId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_customer_by_userId")
    public ResponseEntity getCustomerByUserId(@RequestParam("userId") Long id) throws JsonProcessingException {
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true, customerService.getCustomerByUserId(id), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_customer_by_caId")
    public ResponseEntity getCustomerOfCA(@RequestParam Long id, @RequestParam Integer pageNumber,
                                          @RequestParam Integer pageSize, @RequestParam(required = false) String name) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true, customerService.getCustomerByCAId(id, pageNumber, pageSize, name), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @PutMapping("/update_customer")
    public void UpdateCustomer(@RequestBody Customer customer){
        customerService.updateCustomer(customer);
    }

    @DeleteMapping("/delete_customer")
    public ResponseEntity deleteCustomer(@PathVariable Long customerId) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,customerService.deleteCustomer(customerId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }
 }
