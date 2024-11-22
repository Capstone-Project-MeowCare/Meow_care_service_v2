package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.QuizDto;
import com.meow_care.meow_care_service.dto.QuizWithQuestionsDto;
import com.meow_care.meow_care_service.entities.Quiz;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {QuizQuestionMapper.class})
public interface QuizMapper extends BaseMapper<QuizDto, Quiz> {

    Quiz toEntityWithQuestions(QuizWithQuestionsDto dto);

    QuizWithQuestionsDto toDtoWithQuestions(Quiz entity);

    List<QuizWithQuestionsDto> toDtoWithQuestions(List<Quiz> quizzes);

    Quiz partialUpdate(QuizWithQuestionsDto quizWithQuestionsDto, @MappingTarget Quiz quiz);

    @AfterMapping
    default void linkQuizQuestions(@MappingTarget Quiz quiz) {
        quiz.getQuizQuestions().forEach(quizQuestion -> quizQuestion.setQuiz(quiz));
    }
}