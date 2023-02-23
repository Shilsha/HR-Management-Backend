package com.ca.service;

import com.ca.entity.Document;
import com.ca.model.request.UserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String email;

    public void sendOTPEmail(UserRequestDto userRequest, String otp) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String content = "Hello, <br> You have been successfully added in CABA." +
                " <br> Here is your login OTP : " +
                " <br> <b>" + otp +" </b>";
        helper.setFrom(email);
        helper.setTo(userRequest.getEmail());
        helper.setText(content, true);
        helper.setSubject("CABA OTP VERIFICATION");

        mailSender.send(mimeMessage);
    }

    public void sendDocumentEmail(Document document, String name, String emailCA) throws MessagingException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String createdDate = simpleDateFormat.format(document.getCreatedDate());
        System.out.println("Document url : "+document.getDocUrl());
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String content = "Hello, <br> Here is the copy of uploaded document <br> Link : <br>"+ document.getDocUrl() +
                "<br> Uploaded By : "+ name +
                " <br> Date : " + createdDate;

        helper.setFrom(email);
        helper.setTo(emailCA);
        helper.setSubject("Copy of uploaded document by "+ name);
        helper.setText(content, true);

        mailSender.send(mimeMessage);
    }



}
