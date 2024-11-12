package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.QuizAnswerDto;
import com.meow_care.meow_care_service.dto.QuizQuestionDto;
import com.meow_care.meow_care_service.dto.QuizQuestionWithAnswerDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.QuizQuestion;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.List;
import java.util.UUID;

public interface QuizQuestionService extends BaseService<QuizQuestionDto, QuizQuestion> {
    ApiResponse<QuizQuestionWithAnswerDto> createWithAnswer(UUID quizId, QuizQuestionWithAnswerDto quizQuestionDto);

    ApiResponse<QuizQuestionWithAnswerDto> addAnswerToQuizQuestion(UUID id, List<QuizAnswerDto> answers);

    ApiResponse<QuizQuestionWithAnswerDto> removeAnswerToQuizQuestion(UUID id, List<QuizAnswerDto> answers);
}
