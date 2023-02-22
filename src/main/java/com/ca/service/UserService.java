package com.ca.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ca.entity.*;
import com.ca.exception.BadReqException;
import com.ca.model.request.UserRequestDto;
import com.ca.model.response.SearchResponse;
import com.ca.model.response.UserResponseDto;
import com.ca.repository.*;
import com.ca.utils.Role;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
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
    private AdminRepository adminRepository;
    @Autowired
    private OtpService otpService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AmazonS3 amazonS3;
    @Value("${application.bucket.name}")
    private String bucketName;

    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private SubCARepository subCARepository;

//    <----------------------------------- Add User --------------------------------------->

    public UserResponseDto saveUser(UserRequestDto userRequest) {
        logger.info("Creating new user : {} ", userRequest.getEmail() );

        Optional<User> user1 = Optional.ofNullable(userRepository.findByEmail(userRequest.getEmail()));

        if (user1.isPresent()){
            throw new BadReqException("This email id already exist!!");
        }


        String encodedPassword = null;
        if (userRequest.getPassword() != null){
            encodedPassword = Base64.getEncoder().encodeToString(userRequest.getPassword().getBytes());
            System.out.println("Encoded Password "+ encodedPassword);
        }

        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .address(userRequest.getAddress())
                .mobile(userRequest.getMobile())
                .phone(userRequest.getPhone())
                .password(encodedPassword)
                .role(userRequest.getRole())
                .status(false)
                .otp(otpService.generateOtp())
                .otpVerify(false)
                .profileUrl(null)
                .profileName(null)
                .gender(userRequest.getGender())
                .panCardNumber(userRequest.getPanCardNumber())
                .build();

        userRepository.save(user);

        UserResponseDto userResponse = UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .address(user.getAddress())
                .mobile(user.getMobile())
                .phone(user.getPhone())
                .role(user.getRole())
                .otp(user.getOtp())
                .otpVerify(user.isOtpVerify())
                .status(user.isStatus())
                .createdDate(user.getCreatedDate())
                .modifiedDate(user.getModifiedDate())
                .profileUrl(user.getProfileUrl())
                .profileName(user.getProfileName())
                .panCardNumber(user.getPanCardNumber())
                .gender(user.getGender())
                .build();


        logger.info("User saved successfully in DB : {} ", userResponse.getId());

        if (userRequest.getRole() != null){

            if (userRequest.getRole().equals(Role.ADMIN))
            {
                Admin admin = Admin.builder()
                        .userId(user.getId())
                        .role(userRequest.getAdminRole())
                        .build();

                Admin adminResponse = adminRepository.save(admin);

                logger.info("Admin saved successfully in DB : {}",adminResponse.getId());

            } else if (userResponse.getRole().equals(Role.CA)) {

                CA ca = CA.builder()
                        .userId(user.getId())
                        .adminId(userRequest.getAdminId())
                        .build();

                CA caResponse = caRepository.save(ca);
                logger.info("CA saved successfully in DB : {}",caResponse.getId());

            }else if (userResponse.getRole().equals(Role.CUSTOMER)){

                Customer customer = Customer.builder()
                        .userId(user.getId())
                        .caId(userRequest.getCaId())
                        .build();

                Customer customerResponse = customerRepository.save(customer);
                logger.info("Customer saved successfully in DB : {}",customerResponse.getId());

            } else if (userResponse.getRole().equals(Role.SUBCA)) {

                SubCA subCA = SubCA.builder()
                        .caId(userRequest.getCaId())
                        .addedBy(userRequest.getAddedBy())
                        .userId(user.getId())
                        .build();

                SubCA subCAResponse = subCARepository.save(subCA);
                logger.info("Sub CA saved successfully in DB : {}",subCAResponse.getId());
            }
        }

        emailService.sendEmail(userRequest, user.getOtp());
        return userResponse;
    }

//    <---------------------------------- Get User By Id ------------------------------------->

    public User getUser(Long userId) {
        logger.info("Getting the user id : {}", userId);
        Optional<User> user = Optional.ofNullable(userRepository.findByid(userId));
        if (!user.isPresent()){
            throw new BadReqException("User not present");
        }

        User user1 = user.get();

        byte[] decodedBytes = Base64.getDecoder().decode(user1.getPassword());
        String decodedPassword = new String(decodedBytes);
        user1.setPassword(decodedPassword);
        return user1;
    }

//    <--------------------------------- Get all User --------------------------------------------->

    public List<User> getAllUser(Integer pageNumber, Integer pageSize)
    {
        List<User> users = new ArrayList<>();

        if (pageNumber == -1 && pageSize == -1){
            users = userRepository.findAll();
        }else {
            Pageable pageable = PageRequest.of(pageNumber,pageSize);
            Page<User> pageUser = userRepository.findAll(pageable);
            users = pageUser.getContent();
        }
        logger.info("Getting all user");

        return users;
    }

//    <------------------------------------ Update User -------------------------------------->

    public UserResponseDto updateUser(UserRequestDto userRequest, Long id) {

        Optional<User> user1 = userRepository.findById(id);

        String encodedPassword = null;
        if (userRequest.getPassword() != null){
            encodedPassword = Base64.getEncoder().encodeToString(userRequest.getPassword().getBytes());
            System.out.println("Encoded Password "+ encodedPassword);
        }

        if (user1.isPresent()){

            User user = user1.get();

            if (userRequest.getFirstName() != null){
                user.setFirstName(userRequest.getFirstName());
            }
            if (userRequest.getLastName() != null){
                user.setLastName(userRequest.getLastName());
            }
            if (userRequest.getAddress() != null){
                user.setAddress(userRequest.getAddress());
            }
            if (userRequest.getEmail() != null){
                user.setEmail(userRequest.getEmail());
            }
            if (userRequest.getMobile() != null) {
                user.setMobile(userRequest.getMobile());
            }
            if (userRequest.getPhone() != null){
                user.setPhone(userRequest.getPhone());
            }
            if (userRequest.getPassword() != null){
                user.setPassword(encodedPassword);
            }
            if (userRequest.getPanCardNumber() != null){
                user.setPanCardNumber(userRequest.getPanCardNumber());
            }
            if (userRequest.getGender() != null){
                user.setGender(userRequest.getGender());
            }

            userRepository.save(user);

            UserResponseDto userResponse = UserResponseDto.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .address(user.getAddress())
                    .mobile(user.getMobile())
                    .phone(user.getPhone())
                    .role(user.getRole())
                    .otp(user.getOtp())
                    .otpVerify(user.isOtpVerify())
                    .status(user.isStatus())
                    .createdDate(user.getCreatedDate())
                    .modifiedDate(user.getModifiedDate())
                    .profileUrl(user.getProfileUrl())
                    .profileName(user.getProfileName())
                    .gender(user.getGender())
                    .panCardNumber(user.getPanCardNumber())
                    .build();

            logger.info(id+" updated successfully");
            return userResponse;
        }else {
             throw new BadReqException("User id not present in DB");
        }

    }

//    <------------------------------ Upload Profile Image ---------------------------------------->

    public UserResponseDto uploadImage(Long userId, MultipartFile image){

        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()){
            throw new BadReqException("User id does not exist. "+userId);
        }

        String profileName = image.getOriginalFilename();
        String type = profileName.substring(profileName.lastIndexOf("."));
        String profileNameWithoutExt = FilenameUtils.removeExtension(profileName);
        profileName = profileNameWithoutExt + "" + System.currentTimeMillis() + "" + type;
        logger.info("Profile name {}",profileName);

        User user1 = user.get();

        if (!(type.equals(".jpg") || type.equals(".png"))){
            throw new BadReqException("Image extension is not JPEG or PNG !!");
        }

        Double imageSize = image.getSize() * 0.00000095367432;
        logger.info("Size of image : {}",imageSize);
        if (imageSize > 2){
            throw new BadReqException("Image size is more than 2MB");
        }

        try{
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(image.getSize());
            amazonS3.putObject(new PutObjectRequest(bucketName, profileName, image.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicReadWrite));

            String profileUrl = "https://ebench-images.s3.ap-south-1.amazonaws.com/" + profileName;
            logger.info("Profile URL : {}",profileUrl);

            user1.setProfileUrl(profileUrl);
            user1.setProfileName(profileName);

            userRepository.save(user1);
            logger.info("User profile uploaded successfully");

        } catch (IOException ioe) {
            logger.error("IOException: " + ioe.getMessage());
        } catch (AmazonServiceException serviceException) {
            logger.info("AmazonServiceException: " + serviceException.getMessage());
            throw serviceException;
        } catch (AmazonClientException clientException) {
            logger.info("AmazonClientException Message: " + clientException.getMessage());
            throw clientException;
        }

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
                .build();

        return userResponse;
    }

    //    <------------------------------------Search User By Name---------------------------------------------->

    public List<SearchResponse> searchUser(String name, Role role, Integer pageNumber, Integer pageSize) {
        int userRole = role.ordinal();
        List<User> user1 = new ArrayList<>();

        if (pageNumber == -1 && pageSize == -1){
            user1 = userRepository.findNameStartwith(userRole, name);
        }else {
            Pageable pageable = PageRequest.of(pageNumber,pageSize);
            Page<User> pageUser = userRepository.findNameStartWith(userRole, name, pageable);
            user1 = pageUser.getContent();
        }

        List<SearchResponse> searchResponse = new ArrayList<>();

        for (User user: user1){
            Long id=null;

            if (role.equals(Role.CUSTOMER)){
                Customer customer = customerRepository.findUserId(user.getId());
                id = customer.getId();
            } else if (role.equals(Role.ADMIN)) {
                Admin admin = adminRepository.findByUserId(user.getId());
                id = admin.getId();
            } else if (role.equals(Role.CA)) {
                CA ca = caRepository.findByUserId(user.getId());
                id = ca.getId();
            } else if (role.equals(Role.SUBCA)) {
                SubCA subCA = subCARepository.findByUserId(user.getId());
                id = subCA.getId();
            }

            SearchResponse search = SearchResponse.builder()
                    .id(id)
                    .userId(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .address(user.getAddress())
                    .mobile(user.getMobile())
                    .phone(user.getPhone())
                    .role(role)
                    .build();

            searchResponse.add(search);
        }
        logger.info("Search user whose name start with : {}",name);
        logger.info("Role of the user is : {}",role);
        return searchResponse;
    }

//    <---------------------------------- Delete User ------------------------------------------->

    public User deleteUser(Long userId) {

        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent()){
            throw new BadReqException("User Not present in DB : "+userId);
        }

        User user1 = user.get();
        user1.setStatus(false);
        logger.info(userId+" deleted successfully");
        return userRepository.save(user1);

    }
}