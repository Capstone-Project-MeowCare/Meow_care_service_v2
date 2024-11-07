package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.ConfigServiceDto;
import com.meow_care.meow_care_service.entities.ConfigService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ConfigServiceMapper extends BaseMapper<ConfigServiceDto, ConfigService> {

    @Override
    @Mapping(target = "serviceType.id", source = "serviceTypeId")
    ConfigService toEntity(ConfigServiceDto dto);
}