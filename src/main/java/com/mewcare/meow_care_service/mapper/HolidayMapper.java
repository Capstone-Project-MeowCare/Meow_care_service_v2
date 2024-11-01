package com.mewcare.meow_care_service.mapper;

import com.mewcare.meow_care_service.dto.HolidayDto;
import com.mewcare.meow_care_service.entities.Holiday;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface HolidayMapper extends BaseMapper<HolidayDto, Holiday> {
}