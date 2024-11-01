package com.mewcare.meow_care_service.mapper;

import com.mewcare.meow_care_service.dto.ReviewDto;
import com.mewcare.meow_care_service.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper extends BaseMapper<ReviewDto, Review> {
}