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
    @Mapping(target = "reportedUser.id", source = "reportedUserId")
    Report toEntity(ReportDto dto);

    @Override
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "reportTypeId", source = "reportType.id")
    @Mapping(target = "reportedUserId", source = "reportedUser.id")
    @Mapping(target = "reportedUserEmail", source = "reportedUser.email")
    @Mapping(target = "userEmail", source = "user.email")
    ReportDto toDto(Report entity);
}