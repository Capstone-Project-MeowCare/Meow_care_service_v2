package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.QuizDto;
import com.mewcare.meow_care_service.dto.QuizWithQuestionsDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.entities.Quiz;
import com.mewcare.meow_care_service.enums.ApiStatus;
import com.mewcare.meow_care_service.exception.ApiException;
import com.mewcare.meow_care_service.mapper.QuizMapper;
import com.mewcare.meow_care_service.repositories.QuizRepository;
import com.mewcare.meow_care_service.services.QuizService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QuizServiceImpl extends BaseServiceImpl<QuizDto, Quiz, QuizRepository, QuizMapper>
        implements QuizService {
    public QuizServiceImpl(QuizRepository repository, QuizMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ApiResponse<QuizWithQuestionsDto> getWithQuestions(UUID id) {
        Quiz quiz = repository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        return ApiResponse.success(mapper.toDtoWithQuestions(quiz));
    }

    @Override
    public ApiResponse<QuizWithQuestionsDto> createWithQuestions(QuizWithQuestionsDto dto) {
        Quiz quiz = mapper.toEntityWithQuestions(dto);
        quiz = repository.save(quiz);
        return ApiResponse.success(mapper.toDtoWithQuestions(quiz));
    }
}
