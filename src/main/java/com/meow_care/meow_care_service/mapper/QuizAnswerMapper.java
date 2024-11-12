package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.QuizAnswerDto;
import com.meow_care.meow_care_service.entities.QuizAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuizAnswerMapper extends BaseMapper<QuizAnswerDto, QuizAnswer> {

    List<QuizAnswer> toEntity(List<QuizAnswerDto> answers);

}