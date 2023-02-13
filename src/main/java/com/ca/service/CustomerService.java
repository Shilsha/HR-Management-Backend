package com.ca.service;

import com.ca.entity.Customer;
import com.ca.entity.SubCA;
import com.ca.entity.User;
import com.ca.model.response.CustomerResponseDto;
import com.ca.model.response.SubCAResponseDto;
import com.ca.repository.CustomerRepository;
import com.ca.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public Customer createCustomer(Customer customer){
        logger.info("Create a customer");
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomer(Integer pageNumber, Integer pageSize){
        logger.info("Getting all customers");
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Customer> pageCustomer = customerRepository.findAll(pageable);
        List<Customer> customers = pageCustomer.getContent();
        return customers;
    }

    public Customer getCustomer(Long customerId){
        logger.info("Getting customer id :"+customerId);
        return customerRepository.findById(customerId).get();
    }

    public void updateCustomer(Customer customer, Long customerId){
        Optional<Customer> customer1 = customerRepository.findById(customerId);
        if (customer1.isPresent()){
            logger.info("Updating the customer id :"+customerId);
            Customer customer2 = customer1.get();
            customer2.setUserId(customer.getUserId());
        }
    }

    public void deleteCustomer(Long customerId){
        logger.info("Deleting the customer id:"+customerId);
        customerRepository.deleteById(customerId);
    }

    public List<CustomerResponseDto> getCustomerOfCA(Long caId) {
        logger.info("Getting customer have CA id {}",caId);

        List<Customer> customers = customerRepository.findByCAId(caId);

        List<CustomerResponseDto> customerResponse = new ArrayList<>();

        for (Customer c: customers) {
            User user = userRepository.findById(c.getUserId()).get();
            CustomerResponseDto customer = CustomerResponseDto.builder()
                    .id(c.getId())
                    .userId(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .address(user.getAddress())
                    .mobile(user.getMobile())
                    .phone(user.getPhone())
                    .build();

            customerResponse.add(customer);
        }
        return customerResponse;
    }
}
