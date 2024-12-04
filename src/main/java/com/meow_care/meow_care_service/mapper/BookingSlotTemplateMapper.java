package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.BookingSlotTemplateDto;
import com.meow_care.meow_care_service.entities.BookingSlotTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingSlotTemplateMapper extends BaseMapper<BookingSlotTemplateDto, BookingSlotTemplate> {
}