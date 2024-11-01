package com.mewcare.meow_care_service.mapper;

import com.mewcare.meow_care_service.dto.TaskEvidenceDto;
import com.mewcare.meow_care_service.entities.TaskEvidence;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskEvidenceMapper extends BaseMapper<TaskEvidenceDto, TaskEvidence> {
}