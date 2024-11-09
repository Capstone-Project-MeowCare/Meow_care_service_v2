package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.ReportTypeDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.ReportTypeService;
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
@RequestMapping("/report-types")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class ReportTypeController {

    private final ReportTypeService reportTypeService;

    @GetMapping("/{id}")
    public ApiResponse<ReportTypeDto> getReportTypeById(@PathVariable UUID id) {
        return reportTypeService.get(id);
    }

    @GetMapping
    public ApiResponse<List<ReportTypeDto>> getAllReportTypes() {
        return reportTypeService.getAll();
    }

    @PostMapping
    public ApiResponse<ReportTypeDto> createReportType(@RequestBody ReportTypeDto reportTypeDto) {
        return reportTypeService.create(reportTypeDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<ReportTypeDto> updateReportType(@PathVariable UUID id, @RequestBody ReportTypeDto reportTypeDto) {
        return reportTypeService.update(id, reportTypeDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteReportType(@PathVariable UUID id) {
        return reportTypeService.delete(id);
    }
}
