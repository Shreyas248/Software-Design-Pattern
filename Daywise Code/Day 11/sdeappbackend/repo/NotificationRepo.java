package com.kenny.sdeappbackend.repo;

import com.kenny.sdeappbackend.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepo extends JpaRepository<Notification,Long> {
}
