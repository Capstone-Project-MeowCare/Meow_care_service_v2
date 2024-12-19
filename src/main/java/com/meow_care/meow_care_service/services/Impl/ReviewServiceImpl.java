package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.dto.review.ReviewDto;
import com.meow_care.meow_care_service.dto.review.ReviewRequestDto;
import com.meow_care.meow_care_service.dto.review.ReviewResponseDto;
import com.meow_care.meow_care_service.entities.BookingOrder;
import com.meow_care.meow_care_service.entities.Review;
import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.mapper.ReviewMapper;
import com.meow_care.meow_care_service.repositories.ReviewRepository;
import com.meow_care.meow_care_service.services.BookingOrderService;
import com.meow_care.meow_care_service.services.ReviewService;
import com.meow_care.meow_care_service.services.SitterProfileService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewServiceImpl extends BaseServiceImpl<ReviewDto, Review, ReviewRepository, ReviewMapper> implements ReviewService {

    private final SitterProfileService sitterProfileService;

    private final BookingOrderService bookingOrderService;

    public ReviewServiceImpl(ReviewRepository repository, ReviewMapper mapper, SitterProfileService sitterProfileService, BookingOrderService bookingOrderService) {
        super(repository, mapper);
        this.sitterProfileService = sitterProfileService;
        this.bookingOrderService = bookingOrderService;
    }

    @Override
    public ApiResponse<ReviewResponseDto> createNew(ReviewRequestDto dto) {
        Review entity = mapper.toEntity(dto);
        entity = repository.save(entity);

        BookingOrder bookingOrder = bookingOrderService.findEntityById(dto.bookingOrderId());
        SitterProfile sitterProfile = bookingOrder.getSitter().getSitterProfile();

        //update sitter rating
        List<Review> oldReviews = repository.findByBookingOrder_Sitter_Id(bookingOrder.getSitter().getId());
        double totalRating = 0;
        for (Review review : oldReviews) {
            totalRating += review.getRating();
        }
        double newRating = totalRating / (oldReviews.size());

        sitterProfile = sitterProfileService.updateRating(sitterProfile.getId(), newRating);

        if (sitterProfile == null) {
            return ApiResponse.error(ApiStatus.UPDATE_ERROR, "Update sitter rating failed");
        }

        return ApiResponse.success(mapper.toDtoRes(entity));
    }

    //get list review by user id
    @Override
    public ApiResponse<List<ReviewResponseDto>> getReviewByUserId(UUID userId) {
        List<Review> reviews = repository.findByUserId(userId);
        return ApiResponse.success(mapper.toDtoRes(reviews));
    }

    //get list review by booking order id
    @Override
    public ApiResponse<List<ReviewResponseDto>> getReviewByBookingOrderId(UUID bookingOrderId) {
        List<Review> reviews = repository.findByBookingOrderId(bookingOrderId);
        return ApiResponse.success(mapper.toDtoRes(reviews));
    }

    //get list by sitter id
    @Override
    public ApiResponse<List<ReviewResponseDto>> getReviewBySitterId(UUID sitterId) {
        List<Review> reviews = repository.findByBookingOrder_Sitter_Id(sitterId);
        return ApiResponse.success(mapper.toDtoRes(reviews));
    }

}
