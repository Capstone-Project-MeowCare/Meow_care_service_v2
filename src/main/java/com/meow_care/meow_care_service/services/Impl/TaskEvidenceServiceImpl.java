package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.TaskEvidenceDto;
import com.meow_care.meow_care_service.entities.TaskEvidence;
import com.meow_care.meow_care_service.mapper.TaskEvidenceMapper;
import com.meow_care.meow_care_service.repositories.TaskEvidenceRepository;
import com.meow_care.meow_care_service.services.TaskEvidenceService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TaskEvidenceServiceImpl extends BaseServiceImpl<TaskEvidenceDto, TaskEvidence, TaskEvidenceRepository, TaskEvidenceMapper>
        implements TaskEvidenceService {
    public TaskEvidenceServiceImpl(TaskEvidenceRepository repository, TaskEvidenceMapper mapper) {
        super(repository, mapper);
    }
}
