package com.ca.service;

import com.ca.entity.*;
import com.ca.exception.BadReqException;
import com.ca.model.UserRequestDto;
import com.ca.repository.*;
import com.ca.utils.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CARepository caRepository;
    @Autowired
    private AdminRepopsitory adminRepopsitory;
    @Autowired
    private OtpService otpService;
    @Autowired
    private EmailService emailService;

    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private SubCARepository subCARepository;

    public User saveUser(UserRequestDto userRequest) {
        logger.info("Creating new user : {} ", userRequest.getEmail() );

        Optional<User> user1 = Optional.ofNullable(userRepository.findByEmail(userRequest.getEmail()));

        if (user1.isPresent()){
            throw new BadReqException("This email id already exist!!");
        }

        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .address(userRequest.getAddress())
                .mobile(userRequest.getMobile())
                .phone(userRequest.getPhone())
                .password(userRequest.getPassword())
                .role(userRequest.getRole())
                .status(false)
                .otp(otpService.generateOtp())
                .otpVerify(false)
                .build();

        User userResponse =  userRepository.save(user);
        logger.info("User saved successfully in DB : {} ", userResponse.getId());

        if (userRequest.getRole().equals(Role.ADMIN))
        {
            Admin admin = Admin.builder()
                    .userId(userResponse.getId())
                    .role(userRequest.getAdminRole())
                    .build();

            Admin adminResponse = adminRepopsitory.save(admin);

            logger.info("Admin saved successfully in DB : {}",adminResponse.getId());

        } else if (userResponse.getRole().equals(Role.CA)) {

            CA ca = CA.builder()
                    .userId(userResponse.getId())
                    .adminId(userRequest.getAdminId())
                    .build();

            CA caResponse = caRepository.save(ca);
            logger.info("CA saved successfully in DB : {}",caResponse.getId());

        }else if (userResponse.getRole().equals(Role.CUSTOMER)){

            Customer customer = Customer.builder()
                    .userId(userResponse.getId())
                    .caId(userRequest.getCaId())
                    .build();

            Customer customerResponse = customerRepository.save(customer);
            logger.info("Customer saved successfully in DB : {}",customerResponse.getId());

        } else if (userResponse.getRole().equals(Role.SUBCA)) {

            SubCA subCA = SubCA.builder()
                    .caId(userRequest.getCaId())
                    .addedBy(userRequest.getAddedBy())
                    .userId(userResponse.getId())
                    .build();

            SubCA subCAResponse = subCARepository.save(subCA);
            logger.info("Sub CA saved successfully in DB : {}",subCAResponse.getId());
        }

        emailService.sendEmail(userRequest, user.getOtp());
        return userResponse;
    }

    public User getUser(Long userId) {
        logger.info("Getting the user id : {}", userId);
        return userRepository.findById(userId).get();
    }

    public List<User> getAllUser()
    {
        logger.info("Getting all user");
        return userRepository.findAll();
    }



    public void updateUser(UserRequestDto userRequest, Long userId) {

        Optional<User> user1 = userRepository.findById(userId);

        if (user1.isPresent()){

            User user2 = user1.get();
            user2.setFirstName(userRequest.getFirstName());
            user2.setLastName(userRequest.getLastName());
            user2.setAddress(userRequest.getAddress());
            user2.setEmail(userRequest.getEmail());
            user2.setMobile(userRequest.getMobile());
            user2.setPhone(userRequest.getPhone());
            user2.setPassword(userRequest.getPassword());
            user2.setRole(userRequest.getRole());

            logger.info(userId+" updated successfully");
        }
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
        logger.info(userId+" deleted successfully");
    }
}
