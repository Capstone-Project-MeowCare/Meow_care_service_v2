package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.QuizQuestionDto;
import com.meow_care.meow_care_service.dto.QuizQuestionWithAnswerDto;
import com.meow_care.meow_care_service.entities.QuizQuestion;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {QuizAnswerMapper.class})
public interface QuizQuestionMapper extends BaseMapper<QuizQuestionDto, QuizQuestion> {

    @Mapping(target = "quiz.id", source = "quizId")
    QuizQuestion toEntityWithAnswers(QuizQuestionWithAnswerDto dto);

    QuizQuestionWithAnswerDto toDtoWithAnswers(QuizQuestion entity);

    @AfterMapping
    default void linkQuizAnswers(@MappingTarget QuizQuestion quizQuestion) {
        quizQuestion.getQuizAnswers().forEach(quizAnswer -> quizAnswer.setQuestion(quizQuestion));
    }

}