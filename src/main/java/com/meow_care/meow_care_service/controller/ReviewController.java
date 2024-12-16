package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.dto.review.ReviewDto;
import com.meow_care.meow_care_service.dto.review.ReviewRequestDto;
import com.meow_care.meow_care_service.dto.review.ReviewResponseDto;
import com.meow_care.meow_care_service.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public ApiResponse<ReviewDto> getReviewById(@PathVariable UUID id) {
        return reviewService.get(id);
    }

    @GetMapping
    public ApiResponse<List<ReviewDto>> getAllReviews() {
        return reviewService.getAll();
    }

    @PostMapping
    public ApiResponse<ReviewResponseDto> createReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        return reviewService.createNew(reviewRequestDto);
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<ReviewResponseDto>> getReviewByUserId(@PathVariable UUID userId) {
        return reviewService.getReviewByUserId(userId);
    }

    @GetMapping("/booking-order/{bookingOrderId}")
    public ApiResponse<List<ReviewResponseDto>> getReviewByBookingOrderId(@PathVariable UUID bookingOrderId) {
        return reviewService.getReviewByBookingOrderId(bookingOrderId);
    }

    @PutMapping("/{id}")
    public ApiResponse<ReviewDto> updateReview(@PathVariable UUID id, @RequestBody ReviewDto reviewDto) {
        return reviewService.update(id, reviewDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteReview(@PathVariable UUID id) {
        return reviewService.delete(id);
    }
}
