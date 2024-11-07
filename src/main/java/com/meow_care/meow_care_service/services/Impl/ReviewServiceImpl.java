package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.ReviewDto;
import com.meow_care.meow_care_service.entities.Review;
import com.meow_care.meow_care_service.mapper.ReviewMapper;
import com.meow_care.meow_care_service.repositories.ReviewRepository;
import com.meow_care.meow_care_service.services.ReviewService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl extends BaseServiceImpl<ReviewDto, Review, ReviewRepository, ReviewMapper>
        implements ReviewService {
    public ReviewServiceImpl(ReviewRepository repository, ReviewMapper mapper) {
        super(repository, mapper);
    }
}
