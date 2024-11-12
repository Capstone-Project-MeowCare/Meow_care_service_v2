package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.QuizQuestionDto;
import com.meow_care.meow_care_service.dto.QuizQuestionWithAnswerDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.QuizQuestion;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.QuizQuestionMapper;
import com.meow_care.meow_care_service.repositories.QuizQuestionRepository;
import com.meow_care.meow_care_service.services.QuizQuestionService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QuizQuestionServiceImpl extends BaseServiceImpl<QuizQuestionDto, QuizQuestion, QuizQuestionRepository, QuizQuestionMapper> implements QuizQuestionService {


    public QuizQuestionServiceImpl(QuizQuestionRepository repository, QuizQuestionMapper mapper) {
        super(repository, mapper);
    }


    @Override
    public ApiResponse<QuizQuestionWithAnswerDto> createWithAnswer(UUID quizId, QuizQuestionWithAnswerDto quizQuestionDto) {
        QuizQuestion quizQuestion = mapper.toEntityWithAnswers(quizQuestionDto);
        quizQuestion.setQuizId(quizId);
        QuizQuestion savedQuizQuestion = repository.save(quizQuestion);
        return ApiResponse.success(mapper.toDtoWithAnswers(savedQuizQuestion));
    }

    @Override
    public ApiResponse<QuizQuestionWithAnswerDto> updateWithAnswer(UUID id, QuizQuestionWithAnswerDto quizQuestionDto) {
        QuizQuestion quizQuestion = repository.findById(id).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Quiz question not found")
        );
        mapper.partialUpdate(quizQuestionDto, quizQuestion);
        QuizQuestion savedQuizQuestion = repository.save(quizQuestion);
        return ApiResponse.success(mapper.toDtoWithAnswers(savedQuizQuestion));
    }
}
