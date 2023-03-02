package com.ca.service;

import com.amazonaws.services.apigateway.model.Op;
import com.ca.Apimessage.ApiMessage;
import com.ca.entity.Customer;
import com.ca.entity.User;
import com.ca.exception.BadReqException;
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
import org.springframework.data.domain.Sort;
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

    public List<CustomerResponseDto> getAllCustomer(Integer pageNumber, Integer pageSize){
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

        List<CustomerResponseDto> customerResponse = new ArrayList<>();


        for (User user1: customers) {

            Customer customer = customerRepository.findUserId(user1.getId());
            User ca = userRepository.findById(customer.getCaId()).get();

            CustomerResponseDto customerResponseDto = CustomerResponseDto.builder()
                    .id(customer.getId())
                    .userId(customer.getUserId())
                    .caId(customer.getCaId())
                    .caName(ca.getFirstName())
                    .firstName(user1.getFirstName())
                    .lastName(user1.getLastName())
                    .email(user1.getEmail())
                    .address(user1.getAddress())
                    .mobile(user1.getMobile())
                    .phone(user1.getPhone())
                    .panCardNumber(user1.getPanCardNumber())
                    .aadhaarCardNumber(user1.getAadhaarCardNumber())
                    .gender(user1.getGender())
                    .userResponse(user1.getUserResponse())
                    .createdDate(user1.getCreatedDate())
                    .modifiedDate(user1.getModifiedDate())
                    .profileName(user1.getProfileName())
                    .profileUrl(user1.getProfileUrl())
                    .build();

            customerResponse.add(customerResponseDto);
        }
        return customerResponse;
    }

    public Customer getCustomer(Long customerId){
        logger.info("Getting customer id :"+customerId);
        return customerRepository.findById(customerId).get();
    }

    //TODO
    public void updateCustomer(Customer customer){
        Optional<Customer> customer1 = customerRepository.findById(customer.getId());
        if (customer1.isPresent()){
            logger.info("Updating the customer id :"+customer.getId());
            Customer customer2 = customer1.get();
            customerRepository.save(customer);

        }
    }

    public Customer deleteCustomer(Long customerUserId){
        Optional<Customer> customer = customerRepository.findByUserId(customerUserId);
        if(customer.isPresent())
        {
            Customer customer1= customer.get();
            User user = userRepository.findById(customerUserId).get();
            user.setStatus(false);
            userRepository.save(user);
            customer1.setCustomerStatus(false);
            return customerRepository.save(customer1);
        }else {
            throw new BadReqException(ApiMessage.Customer_not_Deleted);
        }

    }

    public List<CustomerResponseDto> getCustomerByCAId(Long caUserId, Integer pageNumber, Integer pageSize, String name, String sortBy) {
        logger.info("Getting customer by CA id {}",caUserId);
        List<Customer> customers = new ArrayList<>();

// TODO search by name and sorting
//        List<CustomerResponseDto> customerResponse = new ArrayList<>();
//        if(name != null){
//            customerResponse = customerRepository.findByCAIdAndName(caUserId,name);
//        }


        if (pageNumber == -1 && pageSize == -1){
            customers = customerRepository.findByCAId(caUserId);
        }else {
            Pageable pageable = PageRequest.of(pageNumber,pageSize,Sort.by(sortBy).descending());
            Page<Customer> pageCustomer = customerRepository.findByCAid(caUserId,pageable);
            customers = pageCustomer.getContent();
        }

        List<CustomerResponseDto> customerResponse = new ArrayList<>();

        for (Customer c: customers) {

            User user = userRepository.findById(c.getUserId()).get();
            User user1= userRepository.findById(caUserId).get();

            CustomerResponseDto customer = CustomerResponseDto.builder()
                    .id(c.getId())
                    .userId(user.getId())
                    .caId(caUserId)
                    .caName(user1.getFirstName())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .address(user.getAddress())
                    .mobile(user.getMobile())
                    .phone(user.getPhone())
                    .panCardNumber(user.getPanCardNumber())
                    .aadhaarCardNumber(user.getAadhaarCardNumber())
                    .gender(user.getGender())
                    .userResponse(user.getUserResponse())
                    .createdDate(user.getCreatedDate())
                    .modifiedDate(user.getModifiedDate())
                    .profileName(user.getProfileName())
                    .profileUrl(user.getProfileUrl())
                    .build();

            customerResponse.add(customer);
        }
        return customerResponse;
    }

    public UserResponseDto getCustomerByUserId(Long userId) {
        logger.info("Get Customer by user id {}",userId);

        int role = 2;

        Optional<User> user = userRepository.findByUserId(userId,role);
        if (!user.isPresent()){
            throw new BadReqException("Customer Not present in DB id :"+userId);
        }

        User user1 = user.get();

        UserResponseDto userResponse = UserResponseDto.builder()
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
                .userResponse(user1.getUserResponse())
                .build();

        return userResponse;
    }
}
