package com.mewcare.meow_care_service.controller;

import com.mewcare.meow_care_service.dto.NotificationDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{id}")
    public ApiResponse<NotificationDto> getNotificationById(@PathVariable UUID id) {
        return notificationService.get(id);
    }

    @GetMapping
    public ApiResponse<List<NotificationDto>> getAllNotifications() {
        return notificationService.getAll();
    }

    @PostMapping
    public ApiResponse<NotificationDto> createNotification(@RequestBody NotificationDto notificationDto) {
        return notificationService.create(notificationDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<NotificationDto> updateNotification(@PathVariable UUID id, @RequestBody NotificationDto notificationDto) {
        return notificationService.update(id, notificationDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteNotification(@PathVariable UUID id) {
        return notificationService.delete(id);
    }
}
