package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.ServiceTypeDto;
import com.meow_care.meow_care_service.entities.ServiceType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceTypeMapper extends BaseMapper<ServiceTypeDto, ServiceType> {
}