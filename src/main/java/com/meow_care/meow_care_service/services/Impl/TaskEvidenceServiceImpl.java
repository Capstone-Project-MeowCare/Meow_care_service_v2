package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.TaskEvidenceDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.TaskEvidence;
import com.meow_care.meow_care_service.mapper.TaskEvidenceMapper;
import com.meow_care.meow_care_service.repositories.TaskEvidenceRepository;
import com.meow_care.meow_care_service.services.TaskEvidenceService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskEvidenceServiceImpl extends BaseServiceImpl<TaskEvidenceDto, TaskEvidence, TaskEvidenceRepository, TaskEvidenceMapper>
        implements TaskEvidenceService {
    public TaskEvidenceServiceImpl(TaskEvidenceRepository repository, TaskEvidenceMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ApiResponse<TaskEvidenceDto> create(UUID taskId, TaskEvidenceDto taskEvidenceDto) {
        TaskEvidence taskEvidence = mapper.toEntity(taskEvidenceDto);
        taskEvidence.setTaskId(taskId);
        return ApiResponse.success(mapper.toDto(repository.save(taskEvidence)));
    }

    @Override
    public ApiResponse<List<TaskEvidenceDto>> getByTaskId(UUID taskId) {
        List<TaskEvidence> taskEvidences = repository.findByTask_Id(taskId);
        return ApiResponse.success(mapper.toDtoList(taskEvidences));
    }

    @Override
    public ApiResponse<List<TaskEvidenceDto>> createList(UUID taskId, List<TaskEvidenceDto> taskEvidenceDtos) {
        List<TaskEvidence> taskEvidences = mapper.toEntityList(taskEvidenceDtos);
        taskEvidences.forEach(taskEvidence -> taskEvidence.setTaskId(taskId));
        taskEvidences = repository.saveAll(taskEvidences);
        return ApiResponse.success(mapper.toDtoList(taskEvidences));
    }
}
