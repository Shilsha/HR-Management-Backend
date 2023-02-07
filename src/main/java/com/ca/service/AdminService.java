package com.ca.service;

import com.ca.controller.AdminController;
import com.ca.entity.Admin;
import com.ca.repository.AdminRepopsitory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class AdminService {

    @Autowired
    private AdminRepopsitory adminRepopsitory;
    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    public List<Admin> getAllAdmin()
    {
        logger.info("Getting all admin");
        return adminRepopsitory.findAll();
    }

    public Admin getSingleAdmin(Long adminId)
    {
        logger.info("Getting the admin , id :"+adminId);
        return adminRepopsitory.findById(adminId).get();
    }

    public Admin create(Admin admin) {
        logger.info("Creating a new admin");
        adminRepopsitory.save(admin);
        return admin;
    }

    public void updateAdmin(Admin admin, Long adminId) {
        Optional<Admin> admin1 = adminRepopsitory.findById(adminId);
        if (admin1.isPresent())
        {
            Admin admin2 = admin1.get();
            System.out.println(admin2);
            admin2.setRole(admin.getRole());
            admin2.setUserId(admin.getUserId());
            logger.info(adminId +" is updated successfully");
        }

    }

    public void deleteAdmin(Long adminId) {
        adminRepopsitory.deleteById(adminId);
        logger.info(adminId + " is delete successfully");
    }
}
