package com.ca.service;

import com.ca.entity.Customer;
import com.ca.entity.User;
import com.ca.model.response.CustomerResponseDto;
import com.ca.model.response.UserResponseDto;
import com.ca.repository.CustomerRepository;
import com.ca.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public List<UserResponseDto> getAllCustomer(Integer pageNumber, Integer pageSize){
        logger.info("Getting all customers");
        List<User> customers = new ArrayList<>();
        int role = 2; //CUSTOMER

        if (pageNumber == -1 && pageSize == -1){
            customers = userRepository.findByRole(role);
        }else {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<User> pageCustomer = userRepository.findByrole(role,pageable);
            customers = pageCustomer.getContent();
        }

        List<UserResponseDto> userResponse = new ArrayList<>();

        for (User user1: customers) {
            UserResponseDto userResponseDto = UserResponseDto.builder()
                    .id(user1.getId())
                    .firstName(user1.getFirstName())
                    .lastName(user1.getLastName())
                    .email(user1.getEmail())
                    .address(user1.getAddress())
                    .mobile(user1.getMobile())
                    .phone(user1.getPhone())
                    .role(user1.getRole())
                    .otp(user1.getOtp())
                    .otpVerify(user1.isOtpVerify())
                    .status(user1.isStatus())
                    .createdDate(user1.getCreatedDate())
                    .modifiedDate(user1.getModifiedDate())
                    .profileUrl(user1.getProfileUrl())
                    .profileName(user1.getProfileName())
                    .gender(user1.getGender())
                    .panCardNumber(user1.getPanCardNumber())
                    .build();

            userResponse.add(userResponseDto);
        }
        return userResponse;
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
