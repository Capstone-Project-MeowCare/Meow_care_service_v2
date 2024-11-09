package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.ReportDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.ReportService;
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
@RequestMapping("/reports")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/{id}")
    public ApiResponse<ReportDto> getReportById(@PathVariable UUID id) {
        return reportService.get(id);
    }

    @GetMapping
    public ApiResponse<List<ReportDto>> getAllReports() {
        return reportService.getAll();
    }

    @GetMapping("/report-type/{reportTypeId}")
    public ApiResponse<List<ReportDto>> getReportsByReportTypeId(@PathVariable UUID reportTypeId) {
        return reportService.getByReportTypeId(reportTypeId);
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<ReportDto>> getReportsByUserId(@PathVariable UUID userId) {
        return reportService.getByUserId(userId);
    }

    @PostMapping
    public ApiResponse<ReportDto> createReport(@RequestBody ReportDto reportDto) {
        return reportService.create(reportDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<ReportDto> updateReport(@PathVariable UUID id, @RequestBody ReportDto reportDto) {
        return reportService.update(id, reportDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteReport(@PathVariable UUID id) {
        return reportService.delete(id);
    }

}
