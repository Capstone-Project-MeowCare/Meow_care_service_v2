package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.review.ReviewDto;
import com.meow_care.meow_care_service.dto.review.ReviewRequestDto;
import com.meow_care.meow_care_service.dto.review.ReviewResponseDto;
import com.meow_care.meow_care_service.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper extends BaseMapper<ReviewDto, Review> {

    ReviewResponseDto toDtoRes(Review entity);

    List<ReviewResponseDto> toDtoRes(List<Review> entity);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "bookingOrder.id", source = "bookingOrderId")
    Review toEntity(ReviewRequestDto dto);
}