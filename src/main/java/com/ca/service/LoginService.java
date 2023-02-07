package com.ca.service;

import com.ca.config.JwtTokenUtil;
import com.ca.entity.User;
import com.ca.exception.BadReqException;
import com.ca.model.request.LoginRequest;
import com.ca.model.response.LoginResponse;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class LoginService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
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

            User user1 = userRepository.findByEmail(loginRequest.getEmail());
            if (user1 != null){

                try {

                    logger.info("Inside Authentication");
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

                } catch (UsernameNotFoundException e) {
                    e.printStackTrace();
                    throw new Exception("Bad Credentials");
                }catch (BadCredentialsException e)
                {
                    e.printStackTrace();
                    throw new Exception("Bad Credentials");
                }

                User user = userRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());

                final UserDetails userDetails = new org.springframework.security.core.userdetails.User(loginRequest.getEmail(), new BCryptPasswordEncoder().encode(loginRequest.getPassword()), new ArrayList<>());

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
    public boolean verifyEmail(String email, String otp){

        User user = userRepository.findByEmail(email);
        if (otp.length() == 6){
            String otp1 = user.getOtp();

            if(otp1.equals(otp)){
                logger.info("OTP Verified");
                user.setOtpVerify(true);
                user.setStatus(true);
                userRepository.save(user);
                return true;
            }else {
                logger.info("Invalid OTP");
                return false;
            }
        }else {
            throw new BadReqException("Please enter valid otp");
        }
    }
}