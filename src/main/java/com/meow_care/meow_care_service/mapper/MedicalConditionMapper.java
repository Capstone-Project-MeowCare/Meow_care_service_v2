package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.MedicalConditionDto;
import com.meow_care.meow_care_service.entities.MedicalCondition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicalConditionMapper extends BaseMapper<MedicalConditionDto, MedicalCondition> {
}