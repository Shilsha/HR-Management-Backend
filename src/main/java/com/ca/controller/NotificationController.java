package com.ca.controller;

import com.ca.entity.Notification;
import com.ca.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<Notification> getNotificationByUserId(@RequestParam Long userId){
        return notificationService.getNotification(userId);
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
