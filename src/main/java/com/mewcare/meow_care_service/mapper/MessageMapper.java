package com.mewcare.meow_care_service.mapper;

import com.mewcare.meow_care_service.dto.MessageDto;
import com.mewcare.meow_care_service.entities.Message;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageMapper extends BaseMapper<MessageDto, Message> {
}