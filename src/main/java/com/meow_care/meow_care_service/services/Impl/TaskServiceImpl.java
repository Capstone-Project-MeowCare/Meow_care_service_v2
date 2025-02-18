package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.dto.task.TaskDto;
import com.meow_care.meow_care_service.entities.Task;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.TaskStatus;
import com.meow_care.meow_care_service.event.NotificationEvent;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.TaskMapper;
import com.meow_care.meow_care_service.repositories.TaskRepository;
import com.meow_care.meow_care_service.services.TaskService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TaskServiceImpl extends BaseServiceImpl<TaskDto, Task, TaskRepository, TaskMapper>
        implements TaskService {

    private final ScheduledExecutorService scheduledExecutorService;

    private final ApplicationEventPublisher applicationEventPublisher;

    public TaskServiceImpl(TaskRepository repository, TaskMapper mapper, ScheduledExecutorService scheduledExecutorService, ApplicationEventPublisher applicationEventPublisher) {
        super(repository, mapper);
        this.scheduledExecutorService = scheduledExecutorService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @PostConstruct
    public void init() {
        scheduledExecutorService.scheduleAtFixedRate(this::updateInProgressTasks, calculateInitialDelay(), 24 * 60, TimeUnit.MINUTES);
        scheduledExecutorService.scheduleAtFixedRate(this::updatePendingTasks, 0,  1, TimeUnit.HOURS);
    }

    private long calculateInitialDelay() {
        Instant now = Instant.now();
        Instant nextRun = now.truncatedTo(ChronoUnit.DAYS).plus(17, ChronoUnit.HOURS); // 5 PM UTC

        if (now.isAfter(nextRun)) {
            nextRun = nextRun.plus(1, ChronoUnit.DAYS);
        }

        return Duration.between(now, nextRun).toMinutes();
    }

    @Transactional
    protected void updateInProgressTasks() {
        try {
            // Update task status from IN_PROGRESS to NOT_COMPLETED or DONE if the end time is before now and there is no task evidence
            List<Task> inProgressTasks = repository.findByStatusWithTaskEvidence(TaskStatus.IN_PROGRESS);
            for (Task task : inProgressTasks) {
                Instant now = Instant.now();
                if (task.getEndTime() != null && task.getEndTime().isBefore(now)) {
                    if (task.getTaskEvidences() == null || task.getTaskEvidences().isEmpty()) {
                        updateTaskStatus(task.getId(), TaskStatus.NOT_COMPLETED);
                    } else {
                        updateTaskStatus(task.getId(), TaskStatus.DONE);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error updating tasks", e);
        }
    }

    @Transactional
    protected void updatePendingTasks() {
        try {
            Instant now = Instant.now();
            Instant startOfDay = now.atZone(ZoneOffset.UTC).toLocalDate().atStartOfDay().plusHours(20).toInstant(ZoneOffset.UTC);
            if (now.isBefore(startOfDay)) {
                startOfDay = startOfDay.minus(1, ChronoUnit.DAYS);
            }
            Instant endOfDay = startOfDay.plus(1, ChronoUnit.DAYS);


            List<Task> pendingTasks = repository.findByStatusAndStartTimeBetween(TaskStatus.PENDING, startOfDay, endOfDay);

            for (Task task : pendingTasks) {
                if (task.getStartTime() != null && !task.getStartTime().isBefore(startOfDay) && !task.getStartTime().isAfter(endOfDay)) {
                    updateTaskStatus(task.getId(), TaskStatus.IN_PROGRESS);
                }
            }
        } catch (Exception e) {
            log.error("Error updating monthly pending tasks", e);
        }
    }

    @Transactional
    public void updateTaskStatus(UUID id, TaskStatus status) {
        // Fetch the task with assigneeId in one query
        Task task = repository.findById(id).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Task not found with ID: " + id)
        );

        UUID assigneeId = task.getAssigneeId();

        // Publish event based on the new status
        String message = switch (status) {
            case IN_PROGRESS -> "Bạn có một công việc đang diễn ra";
            case DONE -> "Công việc đã hoàn thành";
            case NOT_COMPLETED -> "Công việc chưa hoàn thành";
            default -> null;
        };

        if (message != null && assigneeId != null) {
            applicationEventPublisher.publishEvent(
                    new NotificationEvent(this, assigneeId, message, message)
            );
        }

        // Update the task status in the same transaction
        task.setStatus(status);
        repository.save(task);
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

    @SuppressWarnings("unused")
    public void addChildTask(com.meow_care.meow_care_service.entities.Service service, UUID taskParentId) {
        Task taskParent = repository.findById(taskParentId).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Task not found with ID: " + taskParentId)
        );

        if (taskParent.getStatus() != TaskStatus.PENDING) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Task is not in PENDING status");
        }

        Task task = Task.builder()
                .taskParent(taskParent)
                .name(service.getName())
                .description(service.getActionDescription())
                .status(TaskStatus.PENDING)
                .build();
        repository.save(task);
    }

}
