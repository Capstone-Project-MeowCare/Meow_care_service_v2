package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.dto.review.ReviewDto;
import com.meow_care.meow_care_service.dto.review.ReviewRequestDto;
import com.meow_care.meow_care_service.dto.review.ReviewResponseDto;
import com.meow_care.meow_care_service.entities.Review;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.List;
import java.util.UUID;

public interface ReviewService extends BaseService<ReviewDto, Review> {
    ApiResponse<ReviewResponseDto> createNew(ReviewRequestDto dto);


    //get list review by user id
    ApiResponse<List<ReviewResponseDto>> getReviewByUserId(UUID userId);

    //get list review by booking order id
    ApiResponse<List<ReviewResponseDto>> getReviewByBookingOrderId(UUID bookingOrderId);

    //get list by sitter id
    ApiResponse<List<ReviewResponseDto>> getReviewBySitterId(UUID sitterId);
}
