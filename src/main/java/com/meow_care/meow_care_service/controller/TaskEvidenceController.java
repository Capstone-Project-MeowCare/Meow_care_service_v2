package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.TaskEvidenceDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.TaskEvidenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task-evidences")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class TaskEvidenceController {

    private final TaskEvidenceService taskEvidenceService;

    @GetMapping("/{id}")
    public ApiResponse<TaskEvidenceDto> getTaskEvidenceById(@PathVariable UUID id) {
        return taskEvidenceService.get(id);
    }

    //get task evidences by task id
    @GetMapping("/task/{taskId}")
    public ApiResponse<List<TaskEvidenceDto>> getByTaskId(@PathVariable UUID taskId) {
        return taskEvidenceService.getByTaskId(taskId);
    }

    @GetMapping
    public ApiResponse<List<TaskEvidenceDto>> getAllTaskEvidences() {
        return taskEvidenceService.getAll();
    }

    @PostMapping
    public ApiResponse<TaskEvidenceDto> createTaskEvidence(@RequestParam UUID taskId, @RequestBody TaskEvidenceDto taskEvidenceDto) {
        return taskEvidenceService.create(taskId, taskEvidenceDto);
    }

    //add list of task evidences
    @PostMapping("/list")
    public ApiResponse<List<TaskEvidenceDto>> createTaskEvidences(@RequestParam UUID taskId, @RequestBody List<TaskEvidenceDto> taskEvidenceDtos) {
        return taskEvidenceService.createList(taskId, taskEvidenceDtos);
    }

    @PutMapping("/{id}")
    public ApiResponse<TaskEvidenceDto> updateTaskEvidence(@PathVariable UUID id, @RequestBody TaskEvidenceDto taskEvidenceDto) {
        return taskEvidenceService.update(id, taskEvidenceDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTaskEvidence(@PathVariable UUID id) {
        return taskEvidenceService.delete(id);
    }
}
