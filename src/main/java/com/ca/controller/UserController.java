package com.ca.controller;

import com.ca.Apimessage.ApiMessage;
import com.ca.model.request.UserRequestDto;
import com.ca.repository.UserRepository;
import com.ca.service.UserService;
import com.ca.utils.ApiResponse;
import com.ca.utils.Role;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    //create
    @PostMapping("/add_user")
    public ResponseEntity createUser(@RequestBody UserRequestDto userRequest) throws JsonProcessingException, MessagingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true, userService.saveUser(userRequest), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    //single user get
    @GetMapping("/get_user_by_userId")
    public ResponseEntity getSingleUser(@RequestParam String userId) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,userService.getUser(Long.parseLong(userId)), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_password_by_userId")
    public ResponseEntity getPassword(@RequestParam Long userId) throws JsonProcessingException {
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,userService.getPassword(userId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }
    //all user get
    @GetMapping("/get_all_user")
    public ResponseEntity getAllUser(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,userService.getAllUser(pageNumber, pageSize), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @PutMapping("/update_user")
    public ResponseEntity updateUser(@RequestBody UserRequestDto userRequest,@RequestParam Long id) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,userService.updateUser(userRequest, id), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @PutMapping("/upload_profile_by_userId")
    public ResponseEntity addProfile(@RequestParam Long userId, @RequestParam MultipartFile image) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,userService.uploadImage(userId, image), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @DeleteMapping("/delete_user_by_userId")
    public ResponseEntity deleteUser(@RequestParam Long userId) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,userService.deleteUser(userId), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/search_user_by_name")
    public ResponseEntity searchUser(@RequestParam String name, @RequestParam Role role,@RequestParam Integer pageNumber, @RequestParam Integer pageSize) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,userService.searchUser(name,role, pageNumber, pageSize), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }
}
