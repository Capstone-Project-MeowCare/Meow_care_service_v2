package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.booking_order.CancelBookingRequestDto;
import com.meow_care.meow_care_service.dto.booking_order.CancelBookingResponseDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.BookingOrder;
import com.meow_care.meow_care_service.entities.CancelBooking;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.BookingOrderStatus;
import com.meow_care.meow_care_service.enums.CancelBookingStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.CancelBookingMapper;
import com.meow_care.meow_care_service.repositories.CancelBookingRepository;
import com.meow_care.meow_care_service.services.BookingOrderService;
import com.meow_care.meow_care_service.services.CancelBookingService;
import com.meow_care.meow_care_service.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CancelBookingServiceImpl implements CancelBookingService {

    private final CancelBookingRepository cancelBookingRepository;

    private final CancelBookingMapper cancelBookingMapper;

    private final BookingOrderService bookingOrderService;

    @Override
    public CancelBooking createCancelBookingInternal(CancelBooking cancelBooking) {
        return cancelBookingRepository.save(cancelBooking);
    }

    //create cancel booking
    @Override
    public ApiResponse<CancelBookingResponseDto> createCancelBooking(CancelBookingRequestDto cancelBookingRequestDto) {
        CancelBooking cancelBooking = cancelBookingMapper.toEntity(cancelBookingRequestDto);
        UUID userId = UserUtils.getCurrentUserId();
        BookingOrder bookingOrder = bookingOrderService.findEntityById(cancelBookingRequestDto.bookingOrderId());

        boolean isOwner = bookingOrder.getUser().getId().equals(userId);

        if (isOwner) {
            cancelBooking.setOwnerApprovalAt(Instant.now());
            cancelBooking.setStatus(CancelBookingStatus.OWNER_APPROVED);
        } else {
            cancelBooking.setSitterApprovalAt(Instant.now());
            cancelBooking.setStatus(CancelBookingStatus.SITTER_APPROVED);
        }
        cancelBooking.setRequestedAt(Instant.now());
        cancelBooking = createCancelBookingInternal(cancelBooking);
        return ApiResponse.success(cancelBookingMapper.toDtoRes(cancelBooking));
    }

    //approve cancel booking
    @Override
    public ApiResponse<CancelBookingResponseDto> approveCancelBooking(UUID cancelBookingId) {
        CancelBooking cancelBooking = cancelBookingRepository.findById(cancelBookingId).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Cancel booking not found")
        );
        UUID userId = UserUtils.getCurrentUserId();
        BookingOrder bookingOrder = bookingOrderService.findEntityById(cancelBooking.getBookingOrder().getId());

        boolean isOwner = bookingOrder.getUser().getId().equals(userId);

        if (isOwner) {
            cancelBooking.setOwnerApprovalAt(Instant.now());
            cancelBooking.setStatus(CancelBookingStatus.OWNER_APPROVED);
        } else {
            cancelBooking.setSitterApprovalAt(Instant.now());
            cancelBooking.setStatus(CancelBookingStatus.SITTER_APPROVED);
        }
        cancelBooking = cancelBookingRepository.save(cancelBooking);

        bookingOrderService.updateStatus(bookingOrder.getId(), BookingOrderStatus.CANCELLED);

        return ApiResponse.success(cancelBookingMapper.toDtoRes(cancelBooking));
    }

    //reject cancel booking
    @Override
    public ApiResponse<CancelBookingResponseDto> rejectCancelBooking(UUID cancelBookingId) {
        CancelBooking cancelBooking = cancelBookingRepository.findById(cancelBookingId).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Cancel booking not found")
        );
        cancelBooking.setStatus(CancelBookingStatus.CANCELLED);
        cancelBooking = cancelBookingRepository.save(cancelBooking);
        return ApiResponse.success(cancelBookingMapper.toDtoRes(cancelBooking));
    }


}
