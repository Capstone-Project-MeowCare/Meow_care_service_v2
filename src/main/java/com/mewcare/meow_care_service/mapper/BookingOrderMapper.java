package com.mewcare.meow_care_service.mapper;

import com.mewcare.meow_care_service.dto.BookingOrderDto;
import com.mewcare.meow_care_service.dto.BookingOrderWithDetailDto;
import com.mewcare.meow_care_service.entities.BookingOrder;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {BookingDetailMapper.class})
public interface BookingOrderMapper extends BaseMapper<BookingOrderDto, BookingOrder> {

    @Override
    @Mapping(target = "sitter.id", source = "sitterId")
    BookingOrder toEntity(BookingOrderDto dto);

    @Mapping(target = "sitter.id", source = "sitterId")
    BookingOrder toEntityWithDetail(BookingOrderWithDetailDto dto);

    BookingOrderWithDetailDto toDtoWithDetail(BookingOrder bookingOrder);

    @AfterMapping
    default void linkBookingDetails(@MappingTarget BookingOrder bookingOrder) {
        if (bookingOrder.getBookingDetails() == null) {
            return;
        }
        bookingOrder.getBookingDetails().forEach(bookingDetail -> bookingDetail.setBooking(bookingOrder));
    }

}