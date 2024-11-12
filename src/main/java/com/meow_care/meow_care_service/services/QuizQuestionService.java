package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.QuizQuestionDto;
import com.meow_care.meow_care_service.dto.QuizQuestionWithAnswerDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.QuizQuestion;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.UUID;

public interface QuizQuestionService extends BaseService<QuizQuestionDto, QuizQuestion> {
    ApiResponse<QuizQuestionWithAnswerDto> createWithAnswer(QuizQuestionWithAnswerDto quizQuestionDto);

    ApiResponse<QuizQuestionWithAnswerDto> updateWithAnswer(UUID id, QuizQuestionWithAnswerDto quizQuestionDto);
}
