package com.kenny.sdeappbackend.service;

import com.kenny.sdeappbackend.exception.ResourceNotFoundException;
import com.kenny.sdeappbackend.model.Notification;
import com.kenny.sdeappbackend.repo.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepo notificationRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Notification updateNotification(Long id, Notification notificationDetails) {
        return notificationRepository.findById(id).map(notification -> {
            notification.setMessage(notificationDetails.getMessage());
            notification.setRead(notificationDetails.isRead());
            notification.setUser(notificationDetails.getUser());
            return notificationRepository.save(notification);
        }).orElseThrow(() -> new ResourceNotFoundException("Notification not found with id " + id));
    }

    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
