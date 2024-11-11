package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.care_schedule.CareScheduleDto;
import com.meow_care.meow_care_service.dto.care_schedule.CareScheduleWithTaskDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.CareScheduleService;
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
@RequestMapping("/care-schedules")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class CareScheduleController {

    private final CareScheduleService careScheduleService;


    @GetMapping("/{id}")
    public ApiResponse<CareScheduleWithTaskDto> getCareScheduleById(@PathVariable UUID id) {
        return careScheduleService.getWithTask(id);
    }

    @GetMapping("/booking/{bookingId}")
    public ApiResponse<CareScheduleWithTaskDto> getCareScheduleByBookingId(@PathVariable UUID bookingId) {
        return careScheduleService.getByBookingId(bookingId);
    }

    @GetMapping("/sitter/{sitterId}")
    public ApiResponse<List<CareScheduleWithTaskDto>> getCareSchedulesBySitterId(@PathVariable UUID sitterId) {
        return careScheduleService.getBySitterId(sitterId);
    }

    @GetMapping
    public ApiResponse<List<CareScheduleDto>> getAllCareSchedules() {
        return careScheduleService.getAll();
    }

    @PostMapping
    public ApiResponse<CareScheduleDto> createCareSchedule(@RequestBody CareScheduleDto careScheduleDto) {
        return careScheduleService.create(careScheduleDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<CareScheduleDto> updateCareSchedule(@PathVariable UUID id, @RequestBody CareScheduleDto careScheduleDto) {
        return careScheduleService.update(id, careScheduleDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCareSchedule(@PathVariable UUID id) {
        return careScheduleService.delete(id);
    }
}
