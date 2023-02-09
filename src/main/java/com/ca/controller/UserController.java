package com.ca.controller;

import com.ca.entity.User;
import com.ca.model.UserRequestDto;
import com.ca.repository.UserRepository;
import com.ca.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;

    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequestDto userRequest) {

        User user1 = userService.saveUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    //single user get
    @GetMapping("/u")
    public ResponseEntity<User> getSingleUser(@RequestParam String userId) {
        logger.info("Get Single User Handler: UserController");
        User user = userService.getUser(Long.parseLong(userId));
        logger.info(user.getFirstName());
        return ResponseEntity.ok(user);
    }
    //all user get
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }

    @PutMapping("/{userId}")
    public void updateUser(@RequestBody UserRequestDto userRequest,@PathVariable Long userId){

        userService.updateUser(userRequest, userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }
}
