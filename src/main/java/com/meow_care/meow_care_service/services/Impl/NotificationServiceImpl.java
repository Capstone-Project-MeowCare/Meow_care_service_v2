package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.NotificationDto;
import com.meow_care.meow_care_service.entities.Notification;
import com.meow_care.meow_care_service.mapper.NotificationMapper;
import com.meow_care.meow_care_service.repositories.NotificationRepository;
import com.meow_care.meow_care_service.services.NotificationService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl extends BaseServiceImpl<NotificationDto, Notification, NotificationRepository, NotificationMapper>
        implements NotificationService {
    public NotificationServiceImpl(NotificationRepository repository, NotificationMapper mapper) {
        super(repository, mapper);
    }
}
