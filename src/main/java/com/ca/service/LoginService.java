package com.ca.service;

import com.ca.config.JwtTokenUtil;
import com.ca.entity.User;
import com.ca.exception.BadReqException;
import com.ca.model.request.LoginRequest;
import com.ca.model.request.OtpVerifyRequest;
import com.ca.model.response.LoginResponse;
import com.ca.model.response.UserResponseDto;
import com.ca.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class LoginService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

//    <-------------------------------------- Login for User --------------------------------------------->

    public LoginResponse authenticate(LoginRequest loginRequest) throws Exception {
        Objects.requireNonNull(loginRequest.getEmail());
        Objects.requireNonNull(loginRequest.getPassword());


        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        boolean emailValidation = Pattern.compile(regexPattern)
                .matcher(loginRequest.getEmail())
                .matches();
        System.out.println("Email Validate: " + emailValidation);

        if (emailValidation){

            String encodedPassword = Base64.getEncoder().encodeToString(loginRequest.getPassword().getBytes());
            System.out.println("Password encoded "+ encodedPassword);

            User user = userRepository.findByEmail(loginRequest.getEmail());
            if (user != null){

                if (!encodedPassword.equals(user.getPassword())){
                    logger.info("Password Incorrect");
                    throw new BadReqException("Incorrect Password");
                }

                try {

                    logger.info("Inside Authentication");
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), encodedPassword));

                } catch (UsernameNotFoundException e) {
                    e.printStackTrace();
                    throw new Exception("Bad Credentials");
                }catch (BadCredentialsException e)
                {
                    e.printStackTrace();
                    throw new Exception("Bad Credentials");
                }

                final UserDetails userDetails = new org.springframework.security.core.userdetails.User(loginRequest.getEmail(), loginRequest.getPassword(), new ArrayList<>());

                String token = jwtTokenUtil.generateToken(userDetails);

                LoginResponse loginResponse = LoginResponse.builder()
                        .token(token)
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .address(user.getAddress())
                        .mobile(user.getMobile())
                        .phone(user.getPhone())
                        .role(user.getRole())
                        .build();

                System.out.println("Token generated from candidate " + token);

                logger.info("candidate login successfully");

                return loginResponse;

            }else {
                throw new BadReqException("Email does not exist");
            }

        }else {
            throw new BadReqException("Invalid Email");
        }
    }

//<-------------------------------------- OTP Verification ------------------------------------->
    public UserResponseDto verifyEmail(OtpVerifyRequest otpVerifyRequest){

        Optional<User> user1 = Optional.ofNullable(userRepository.findByEmail(otpVerifyRequest.getEmail()));

        if (!user1.isPresent()){
            throw new BadReqException("Incorrect email");
        }
         User user = user1.get();
        String otp = otpVerifyRequest.getOtp();

        if (otp.length() == 6){
            String otp1 = user.getOtp();

            if(otp1.equals(otp)){
                logger.info("OTP Verified");
                user.setOtpVerify(true);
                user.setStatus(true);
                userRepository.save(user);
            }else {
                logger.info("Invalid OTP");
                throw new BadReqException("Incorrect OTP");
            }
        }else {
            throw new BadReqException("Please enter valid otp");
        }

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
                .build();

        return userResponse;
    }
}