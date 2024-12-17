package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.booking_order.CancelBookingDto;
import com.meow_care.meow_care_service.dto.booking_order.CancelBookingRequestDto;
import com.meow_care.meow_care_service.dto.booking_order.CancelBookingResponseDto;
import com.meow_care.meow_care_service.entities.CancelBooking;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CancelBookingMapper extends BaseMapper<CancelBookingDto, CancelBooking> {

  CancelBookingResponseDto toDtoRes(CancelBooking entity);

  CancelBooking toEntity(CancelBookingRequestDto dto);
}