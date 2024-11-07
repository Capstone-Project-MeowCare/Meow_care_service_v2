package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.QuizDto;
import com.meow_care.meow_care_service.dto.QuizWithQuestionsDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Quiz;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.UUID;

public interface QuizService extends BaseService<QuizDto, Quiz> {
    ApiResponse<QuizWithQuestionsDto> getWithQuestions(UUID id);

    ApiResponse<QuizWithQuestionsDto> createWithQuestions(QuizWithQuestionsDto dto);
}
