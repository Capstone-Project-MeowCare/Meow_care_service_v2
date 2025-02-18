package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.booking_order.BookingOrderDto;
import com.meow_care.meow_care_service.dto.booking_order.BookingOrderRequest;
import com.meow_care.meow_care_service.dto.booking_order.BookingOrderWithDetailDto;
import com.meow_care.meow_care_service.entities.BookingOrder;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {BookingDetailMapper.class, UserMapper.class})
public interface BookingOrderMapper extends BaseMapper<BookingOrderDto, BookingOrder> {

    @Override
    @Mapping(target = "sitter.id", source = "sitterId")
    BookingOrder toEntity(BookingOrderDto dto);

    @Mapping(target = "sitter.id", source = "sitterId")
    @Mapping(target = "user", ignore = true)
    BookingOrder toEntityWithDetail(BookingOrderWithDetailDto dto);

    @Mapping(target = "sitter.id", source = "sitterId")
    @Mapping(target = "user", ignore = true)
    BookingOrder toEntityWithDetail(BookingOrderRequest dto);

    @Mapping(target = "bookingDetailWithPetAndServices", source = "bookingDetails")
    @Mapping(target = "bookingDetails", ignore = true)
    BookingOrderWithDetailDto toDtoWithDetail(BookingOrder bookingOrder);

    //to dto with detail list
    List<BookingOrderWithDetailDto> toDtoWithDetail(List<BookingOrder> bookingOrders);

    @AfterMapping
    default void linkBookingDetails(@MappingTarget BookingOrder bookingOrder) {
        if (bookingOrder.getBookingDetails() == null) {
            return;
        }
        bookingOrder.getBookingDetails().forEach(bookingDetail -> bookingDetail.setBooking(bookingOrder));
    }

}