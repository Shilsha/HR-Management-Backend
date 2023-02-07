package com.ca.controller;

import com.ca.entity.Admin;
import com.ca.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping
    public Admin create(@RequestBody Admin admin){
        return adminService.create(admin);
    }

    @GetMapping
    public List<Admin> getAllAdmin(){
        return adminService.getAllAdmin();
    }

    @GetMapping("/{adminId}")
    public Admin getAdmin(@PathVariable Long adminId){
        return adminService.getSingleAdmin(adminId);
    }

    @PutMapping("/{adminId}")
    public void updateAdmin(@RequestBody Admin admin,@PathVariable Long adminId){

        adminService.updateAdmin(admin,adminId);
    }

    @DeleteMapping("/{adminId}")
    public void delete(@PathVariable Long adminId){
        adminService.deleteAdmin(adminId);
    }
}
