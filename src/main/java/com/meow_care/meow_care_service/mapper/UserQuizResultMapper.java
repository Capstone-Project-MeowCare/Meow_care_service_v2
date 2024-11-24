package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.quiz.UserQuizResultDto;
import com.meow_care.meow_care_service.dto.quiz.UserQuizResultWithQuizDto;
import com.meow_care.meow_care_service.entities.UserQuizResult;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {QuizMapper.class})
public interface UserQuizResultMapper extends BaseMapper<UserQuizResultDto, UserQuizResult> {

    UserQuizResultWithQuizDto toDtoWithQuiz(UserQuizResult userQuizResult);

    List<UserQuizResultWithQuizDto> toDtoWithQuizList(List<UserQuizResult> userQuizResults);

}