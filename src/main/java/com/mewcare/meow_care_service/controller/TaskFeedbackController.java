package com.mewcare.meow_care_service.controller;

import com.mewcare.meow_care_service.dto.TaskFeedbackDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.services.TaskFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task-feedbacks")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class TaskFeedbackController {

    private final TaskFeedbackService taskFeedbackService;

    @GetMapping("/{id}")
    public ApiResponse<TaskFeedbackDto> getTaskFeedbackById(@PathVariable UUID id) {
        return taskFeedbackService.get(id);
    }

    @GetMapping
    public ApiResponse<List<TaskFeedbackDto>> getAllTaskFeedbacks() {
        return taskFeedbackService.getAll();
    }

    @PostMapping
    public ApiResponse<TaskFeedbackDto> createTaskFeedback(@RequestBody TaskFeedbackDto taskFeedbackDto) {
        return taskFeedbackService.create(taskFeedbackDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<TaskFeedbackDto> updateTaskFeedback(@PathVariable UUID id, @RequestBody TaskFeedbackDto taskFeedbackDto) {
        return taskFeedbackService.update(id, taskFeedbackDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTaskFeedback(@PathVariable UUID id) {
        return taskFeedbackService.delete(id);
    }
}
