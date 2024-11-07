package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.HistoryDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.HistoryService;
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
@RequestMapping("/histories")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/{id}")
    public ApiResponse<HistoryDto> getHistoryById(@PathVariable UUID id) {
        return historyService.get(id);
    }

    @GetMapping
    public ApiResponse<List<HistoryDto>> getAllHistories() {
        return historyService.getAll();
    }

    @PostMapping
    public ApiResponse<HistoryDto> createHistory(@RequestBody HistoryDto historyDto) {
        return historyService.create(historyDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<HistoryDto> updateHistory(@PathVariable UUID id, @RequestBody HistoryDto historyDto) {
        return historyService.update(id, historyDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteHistory(@PathVariable UUID id) {
        return historyService.delete(id);
    }
}
