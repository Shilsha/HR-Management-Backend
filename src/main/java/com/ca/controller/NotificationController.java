package com.ca.controller;

import com.ca.entity.Notification;
import com.ca.repository.NotificationRepository;
import com.ca.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping
    public List<Notification> getNotificationByUserId(@RequestParam Long userId){
        return notificationService.getNotification(userId);
    }

    @GetMapping("/all")
    public List<Notification> getAllNotification(@RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        return notificationService.getAllNotification(pageNumber,pageSize);
    }

    @PostMapping
    public Notification addNotification(@RequestBody Notification notification){
        return notificationService.addNotification(notification);
    }

    @PutMapping
    public void updateReadStatus(@RequestParam Long id, @RequestParam Boolean status){
        notificationService.updateReadStatus(id,status);
    }

}
