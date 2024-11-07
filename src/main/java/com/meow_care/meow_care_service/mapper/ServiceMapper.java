package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.ServiceDto;
import com.meow_care.meow_care_service.entities.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceMapper extends BaseMapper<ServiceDto, Service> {

    @Override
    @Mapping(target = "configService.id", source = "configServiceId")
    Service toEntity(ServiceDto dto);
}