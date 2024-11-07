package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.TaskDto;
import com.meow_care.meow_care_service.entities.Task;
import com.meow_care.meow_care_service.mapper.TaskMapper;
import com.meow_care.meow_care_service.repositories.TaskRepository;
import com.meow_care.meow_care_service.services.TaskService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl extends BaseServiceImpl<TaskDto, Task, TaskRepository, TaskMapper>
        implements TaskService {
    public TaskServiceImpl(TaskRepository repository, TaskMapper mapper) {
        super(repository, mapper);
    }
}
