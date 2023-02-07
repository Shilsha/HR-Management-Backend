package com.ca.service;

import com.ca.controller.UserController;
import com.ca.entity.Notification;
import com.ca.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    public List<Notification> getNotification(Long userId) {
        logger.info("Get notification of user : {}",userId);
        return notificationRepository.findByUserId(userId);
    }

    public Notification addNotification(Notification notification) {
        logger.info("Adding new notification in DB");
        return notificationRepository.save(notification);
    }

    public void updateReadStatus(Long id, Boolean status) {
        Optional<Notification> notification = notificationRepository.findById(id);
        logger.info("Update the read status of notification id :{}",id);

        if (notification.isPresent()){
            Notification notification1 = notification.get();
            notification1.setReadStatus(status);
            notificationRepository.save(notification1);
        }
    }

    public List<Notification> getAllNotification() {
        logger.info("Getting all notification");
        return notificationRepository.findAll();
    }
}
