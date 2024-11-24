package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.quiz.QuizQuestionDto;
import com.meow_care.meow_care_service.dto.quiz.QuizQuestionWithAnswerDto;
import com.meow_care.meow_care_service.dto.quiz.UserQuizQuestionResponse;
import com.meow_care.meow_care_service.entities.QuizQuestion;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {QuizAnswerMapper.class})
public interface QuizQuestionMapper extends BaseMapper<QuizQuestionDto, QuizQuestion> {

    QuizQuestion toEntityWithAnswers(QuizQuestionWithAnswerDto dto);

    QuizQuestionWithAnswerDto toDtoWithAnswers(QuizQuestion entity);

    void partialUpdate(QuizQuestionWithAnswerDto quizQuestionDto,@MappingTarget QuizQuestion quizQuestion);

    List<QuizQuestion> toEntityWithAnswers(List<QuizQuestionWithAnswerDto> quizWithQuestionsDto);

    UserQuizQuestionResponse toUserQuizQuestionResponse(QuizQuestion entity);

    List<UserQuizQuestionResponse> toUserQuizQuestionResponses(List<QuizQuestion> entities);

    @AfterMapping
    default void linkQuizAnswers(@MappingTarget QuizQuestion quizQuestion) {
        quizQuestion.getQuizAnswers().forEach(quizAnswer -> quizAnswer.setQuestion(quizQuestion));
    }
}