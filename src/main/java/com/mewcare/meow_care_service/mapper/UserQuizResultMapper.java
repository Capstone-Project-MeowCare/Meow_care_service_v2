package com.mewcare.meow_care_service.mapper;

import com.mewcare.meow_care_service.dto.UserQuizResultDto;
import com.mewcare.meow_care_service.entities.UserQuizResult;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserQuizResultMapper extends BaseMapper<UserQuizResultDto, UserQuizResult> {
}