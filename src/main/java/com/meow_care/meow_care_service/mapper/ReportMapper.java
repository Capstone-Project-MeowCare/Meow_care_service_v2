package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.ReportDto;
import com.meow_care.meow_care_service.entities.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportMapper extends BaseMapper<ReportDto, Report> {

    @Override
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "reportType.id", source = "reportTypeId")
    Report toEntity(ReportDto dto);
}