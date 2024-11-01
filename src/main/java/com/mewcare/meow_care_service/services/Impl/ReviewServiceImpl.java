package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.ReviewDto;
import com.mewcare.meow_care_service.entities.Review;
import com.mewcare.meow_care_service.mapper.ReviewMapper;
import com.mewcare.meow_care_service.repositories.ReviewRepository;
import com.mewcare.meow_care_service.services.ReviewService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;

public class ReviewServiceImpl extends BaseServiceImpl<ReviewDto, Review, ReviewRepository, ReviewMapper>
        implements ReviewService {
    public ReviewServiceImpl(ReviewRepository repository, ReviewMapper mapper) {
        super(repository, mapper);
    }
}
