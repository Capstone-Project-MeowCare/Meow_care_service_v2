package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.TaskEvidenceDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.TaskEvidence;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.UUID;

public interface TaskEvidenceService extends BaseService<TaskEvidenceDto, TaskEvidence> {
    ApiResponse<TaskEvidenceDto> create(UUID taskId, TaskEvidenceDto taskEvidenceDto);
}
