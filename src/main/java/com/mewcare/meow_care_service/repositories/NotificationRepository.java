package com.mewcare.meow_care_service.repositories;

import com.mewcare.meow_care_service.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}