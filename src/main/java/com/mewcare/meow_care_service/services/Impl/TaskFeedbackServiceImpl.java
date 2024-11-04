package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.TaskFeedbackDto;
import com.mewcare.meow_care_service.entities.TaskFeedback;
import com.mewcare.meow_care_service.mapper.TaskFeedbackMapper;
import com.mewcare.meow_care_service.repositories.TaskFeedbackRepository;
import com.mewcare.meow_care_service.services.TaskFeedbackService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TaskFeedbackServiceImpl extends BaseServiceImpl<TaskFeedbackDto, TaskFeedback, TaskFeedbackRepository, TaskFeedbackMapper>
        implements TaskFeedbackService {
    public TaskFeedbackServiceImpl(TaskFeedbackRepository repository, TaskFeedbackMapper mapper) {
        super(repository, mapper);
    }
}
