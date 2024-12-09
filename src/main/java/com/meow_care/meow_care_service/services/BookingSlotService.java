package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.BookingSlotDto;
import com.meow_care.meow_care_service.dto.BookingSlotTemplateDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.BookingSlot;
import com.meow_care.meow_care_service.enums.BookingSlotStatus;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookingSlotService extends BaseService<BookingSlotDto, BookingSlot> {
    //method to create booking slot
    ApiResponse<BookingSlotTemplateDto> create(BookingSlotTemplateDto dto);

    //method to assign service to booking slot
    ApiResponse<Void> assignService(UUID bookingSlotTemplateId, UUID serviceId);

    //get all booking slot template by user id
    ApiResponse<List<BookingSlotTemplateDto>> getAllByUserId(UUID userId);

    ApiResponse<List<BookingSlotTemplateDto>> getByServiceIdAndTime(UUID serviceId, Instant startDate, Instant endDate);

    //get all booking slot by sitter id, date and status
    ApiResponse<List<BookingSlotDto>> getBySitterIdDateAndStatus(UUID sitterId, LocalDate date, BookingSlotStatus status);

    //get all booking slot by sitter id, service id, date and status
    ApiResponse<List<BookingSlotDto>> getBySitterIdServiceIdDateAndStatus(UUID sitterId, UUID serviceId, LocalDate date, BookingSlotStatus status);
}
