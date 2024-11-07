package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.HolidayDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.HolidayService;
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
@RequestMapping("/holidays")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class HolidayController {

    private final HolidayService holidayService;

    @GetMapping("/{id}")
    public ApiResponse<HolidayDto> getHolidayById(@PathVariable UUID id) {
        return holidayService.get(id);
    }

    @GetMapping
    public ApiResponse<List<HolidayDto>> getAllHolidays() {
        return holidayService.getAll();
    }

    @PostMapping
    public ApiResponse<HolidayDto> createHoliday(@RequestBody HolidayDto holidayDto) {
        return holidayService.create(holidayDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<HolidayDto> updateHoliday(@PathVariable UUID id, @RequestBody HolidayDto holidayDto) {
        return holidayService.update(id, holidayDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteHoliday(@PathVariable UUID id) {
        return holidayService.delete(id);
    }
}
