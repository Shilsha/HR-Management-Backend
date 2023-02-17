package com.ca.service;

import com.ca.controller.AdminController;
import com.ca.entity.Admin;
import com.ca.entity.User;
import com.ca.model.response.UserResponseDto;
import com.ca.repository.AdminRepository;
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
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    private Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private UserRepository userRepository;

    public List<UserResponseDto> getAllAdmin(Integer pageNumber, Integer pageSize)
    {
        logger.info("Getting all admin");
        int role = 0;
        List<User> users = new ArrayList<>();

        if (pageNumber == -1 && pageSize == -1){
            users = userRepository.findByRole(role);
        }else {
            Pageable pageable = PageRequest.of(pageNumber,pageSize);
            Page<User> userPage = userRepository.findByrole(role, pageable);
            users = userPage.getContent();
        }

        List<UserResponseDto> userResponse = new ArrayList<>();

        for (User user1: users){
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

    public Admin getSingleAdmin(Long adminId)
    {
        logger.info("Getting the admin , id :"+adminId);
        return adminRepository.findById(adminId).get();
    }

    public Admin create(Admin admin) {
        logger.info("Creating a new admin");
        adminRepository.save(admin);
        return admin;
    }

    public void updateAdmin(Admin admin, Long adminId) {
        Optional<Admin> admin1 = adminRepository.findById(adminId);
        if (admin1.isPresent())
        {
            Admin admin2 = admin1.get();
            System.out.println(admin2);
            admin2.setRole(admin.getRole());
            admin2.setUserId(admin.getUserId());
            logger.info(adminId +" is updated successfully");
        }

    }

    public void deleteAdmin(Long adminId) {
        adminRepository.deleteById(adminId);
        logger.info(adminId + " is delete successfully");
    }
}
