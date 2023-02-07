package com.ca.service;

import com.ca.model.UserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(UserRequestDto userRequest, String otp) {
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mauryasaurabh49712@gmail.com");
        message.setTo(userRequest.getEmail());
        message.setText(userRequest.getFirstName()+" Registered Successfully \n OTP :" + otp);
        message.setSubject("User Registration");

        mailSender.send(message);
    }
}
