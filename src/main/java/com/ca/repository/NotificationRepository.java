package com.ca.repository;

import com.ca.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

   @Query(value = "select * from notification n where user_id=?1", nativeQuery = true)
    List<Notification> findByUserId(Long userId);
}
