package com.mewcare.meow_care_service.mapper;

import com.mewcare.meow_care_service.dto.CareScheduleDto;
import com.mewcare.meow_care_service.entities.CareSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CareScheduleMapper extends BaseMapper<CareScheduleDto, CareSchedule> {
}