package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.booking_order.CancelBookingDto;
import com.meow_care.meow_care_service.dto.booking_order.CancelBookingRequestDto;
import com.meow_care.meow_care_service.dto.booking_order.CancelBookingResponseDto;
import com.meow_care.meow_care_service.entities.CancelBooking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {BookingOrderMapper.class})
public interface CancelBookingMapper extends BaseMapper<CancelBookingDto, CancelBooking>{

  CancelBookingResponseDto toDtoRes(CancelBooking entity);

  @Mapping(target = "bookingOrder.id", source = "bookingOrderId")
  CancelBooking toEntity(CancelBookingRequestDto dto);
}