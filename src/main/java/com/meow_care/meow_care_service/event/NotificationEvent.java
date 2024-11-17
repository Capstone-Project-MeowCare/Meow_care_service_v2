package com.meow_care.meow_care_service.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class NotificationEvent  extends ApplicationEvent {

    private final UUID userId;
    private final String title;
    private final String message;

    public NotificationEvent(Object source, UUID userId, String title, String message) {
        super(source);
        this.userId = userId;
        this.title = title;
        this.message = message;
    }
}
