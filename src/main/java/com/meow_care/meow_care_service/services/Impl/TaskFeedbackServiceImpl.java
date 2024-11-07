package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.TaskFeedbackDto;
import com.meow_care.meow_care_service.entities.TaskFeedback;
import com.meow_care.meow_care_service.mapper.TaskFeedbackMapper;
import com.meow_care.meow_care_service.repositories.TaskFeedbackRepository;
import com.meow_care.meow_care_service.services.TaskFeedbackService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TaskFeedbackServiceImpl extends BaseServiceImpl<TaskFeedbackDto, TaskFeedback, TaskFeedbackRepository, TaskFeedbackMapper>
        implements TaskFeedbackService {
    public TaskFeedbackServiceImpl(TaskFeedbackRepository repository, TaskFeedbackMapper mapper) {
        super(repository, mapper);
    }
}
