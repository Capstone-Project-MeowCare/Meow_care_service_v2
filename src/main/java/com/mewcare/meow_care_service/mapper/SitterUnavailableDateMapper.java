package com.mewcare.meow_care_service.mapper;

import com.mewcare.meow_care_service.dto.SitterUnavailableDateDto;
import com.mewcare.meow_care_service.entities.SitterUnavailableDate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SitterUnavailableDateMapper extends BaseMapper<SitterUnavailableDateDto, SitterUnavailableDate> {
}