package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.dto.task.TaskDto;
import com.meow_care.meow_care_service.entities.Task;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.UUID;

public interface TaskService extends BaseService<TaskDto, Task> {
    ApiResponse<Boolean> isHaveEvidence(UUID id);
}
