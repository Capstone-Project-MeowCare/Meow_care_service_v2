package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.dto.task.TaskDto;
import com.meow_care.meow_care_service.entities.Task;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.TaskStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.TaskMapper;
import com.meow_care.meow_care_service.repositories.TaskRepository;
import com.meow_care.meow_care_service.services.TaskService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TaskServiceImpl extends BaseServiceImpl<TaskDto, Task, TaskRepository, TaskMapper>
        implements TaskService {

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public TaskServiceImpl(TaskRepository repository, TaskMapper mapper) {
        super(repository, mapper);
    }

    @PostConstruct
    public void init() {
        scheduledExecutorService.scheduleAtFixedRate(this::updatePendingAndInProgressTasks, 1, 1, TimeUnit.MINUTES);
    }

    protected void updatePendingAndInProgressTasks() {
        try {
            List<Task> pendingTasks = repository.findByStatus(TaskStatus.PENDING);
            for (Task task : pendingTasks) {
                boolean statusChanged = false;
                Instant now = Instant.now();

                if (task.getStartTime() != null && task.getStartTime().isBefore(now)) {
                    task.setStatus(TaskStatus.IN_PROGRESS);
                    statusChanged = true;
                }
                if (statusChanged) {
                    repository.save(task);
                }
            }

            List<Task> inProgressTasks = repository.findByStatusWithTaskEvidence(TaskStatus.IN_PROGRESS);
            for (Task task : inProgressTasks) {
                boolean statusChanged = false;
                Instant now = Instant.now();

                if (task.getEndTime() != null && task.getEndTime().isBefore(now)) {
                    if (task.getTaskEvidences() == null || task.getTaskEvidences().isEmpty()) {
                        task.setStatus(TaskStatus.NOT_COMPLETED);
                    } else {
                        task.setStatus(TaskStatus.DONE);
                    }
                    statusChanged = true;
                }
                if (statusChanged) {
                    repository.save(task);
                }
            }
            log.info("Tasks updated");
        } catch (Exception e) {
            log.error("Error updating tasks", e);
        }
    }

    @Override
    public ApiResponse<Boolean> isHaveEvidence(UUID id) {
        Task task = repository.findById(id).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Task not found")
        );
        if (task.getTaskEvidences() != null && !task.getTaskEvidences().isEmpty()) {
            return ApiResponse.success(true);
        }
        return ApiResponse.success(false);
    }
}
