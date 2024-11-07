package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.NotificationDto;
import com.meow_care.meow_care_service.entities.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper extends BaseMapper<NotificationDto, Notification> {
}