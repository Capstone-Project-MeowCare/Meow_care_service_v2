package com.mewcare.meow_care_service.controller;

import com.mewcare.meow_care_service.dto.TaskDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.services.TaskService;
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
@RequestMapping("/tasks")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    public ApiResponse<TaskDto> getTaskById(@PathVariable UUID id) {
        return taskService.get(id);
    }

    @GetMapping
    public ApiResponse<List<TaskDto>> getAllTasks() {
        return taskService.getAll();
    }

    @PostMapping
    public ApiResponse<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        return taskService.create(taskDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<TaskDto> updateTask(@PathVariable UUID id, @RequestBody TaskDto taskDto) {
        return taskService.update(id, taskDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTask(@PathVariable UUID id) {
        return taskService.delete(id);
    }
}
