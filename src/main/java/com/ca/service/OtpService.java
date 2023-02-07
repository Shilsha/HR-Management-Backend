package com.ca.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;

@Service
public class OtpService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public String generateOtp(){

        String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
        logger.info("Opt generated successfully : {}",otp);
        return otp;
    }
}
