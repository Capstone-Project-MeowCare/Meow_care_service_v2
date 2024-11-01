package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.NotificationDto;
import com.mewcare.meow_care_service.entities.Notification;
import com.mewcare.meow_care_service.mapper.NotificationMapper;
import com.mewcare.meow_care_service.repositories.NotificationRepository;
import com.mewcare.meow_care_service.services.NotificationService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;

public class NotificationServiceImpl extends BaseServiceImpl<NotificationDto, Notification, NotificationRepository, NotificationMapper>
        implements NotificationService {
    public NotificationServiceImpl(NotificationRepository repository, NotificationMapper mapper) {
        super(repository, mapper);
    }
}
