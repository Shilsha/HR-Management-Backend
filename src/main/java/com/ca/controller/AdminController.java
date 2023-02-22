package com.ca.controller;

import com.ca.Apimessage.ApiMessage;
import com.ca.entity.Admin;
import com.ca.service.AdminService;
import com.ca.utils.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/create_admin")
    public ResponseEntity create(@RequestBody Admin admin) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,adminService.create(admin), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_all_admin")
    public ResponseEntity getAllAdmin(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) throws JsonProcessingException {

        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK,true,adminService.getAllAdmin(pageNumber, pageSize), ApiMessage.Api_Message);
        return apiResponse.getResponse(apiResponse);
    }

    @GetMapping("/get_admin_by_adminId")
    public Admin getAdmin(@RequestParam Long adminId){
        return adminService.getSingleAdmin(adminId);
    }

    @PutMapping("/update_admin")
    public void updateAdmin(@RequestBody Admin admin){

        adminService.updateAdmin(admin);
    }

    @DeleteMapping("/delete_by_adminId")
    public void delete(@RequestParam Long adminId){
        adminService.deleteAdmin(adminId);
    }
}
