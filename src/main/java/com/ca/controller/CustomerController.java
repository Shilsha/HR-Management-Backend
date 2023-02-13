package com.ca.controller;

import com.ca.entity.Customer;
import com.ca.model.response.CustomerResponseDto;
import com.ca.repository.CustomerRepository;
import com.ca.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Customer create(@RequestBody Customer customer){
        return customerService.createCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomer(@RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        return customerService.getAllCustomer(pageNumber, pageSize);
    }

    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable("customerId") Long customerId){
        return customerService.getCustomer(customerId);
    }

    @GetMapping("/{caId}")
    public List<CustomerResponseDto> getCustomerOfCA(@RequestParam Long caId){
        return customerService.getCustomerOfCA(caId);
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
