package com.meow_care.meow_care_service.event;

import com.meow_care.meow_care_service.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;

    @EventListener
    public void handleNotificationEvent(NotificationEvent notificationEvent) {
        notificationService.saveNotification(notificationEvent.getUserId(), notificationEvent.getTitle(), notificationEvent.getMessage());
    }

}
