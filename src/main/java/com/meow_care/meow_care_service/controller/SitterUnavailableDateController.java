package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.SitterUnavailableDateDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.SitterUnavailableDateService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/sitter-unavailable-dates")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class SitterUnavailableDateController {

    private final SitterUnavailableDateService sitterUnavailableDateService;

    @GetMapping("/{id}")
    public ApiResponse<SitterUnavailableDateDto> getSitterUnavailableDateById(@PathVariable UUID id) {
        return sitterUnavailableDateService.get(id);
    }

    @GetMapping("/sitter/{id}")
    @Operation(summary = "Get sitter unavailable date by sitter id")
    public ResponseEntity<List<SitterUnavailableDateDto>> getSitterUnavailableDateBySitterId(@PathVariable UUID id) {
        return sitterUnavailableDateService.findBySitterId(id);
    }

    @GetMapping
    public ApiResponse<List<SitterUnavailableDateDto>> getAllSitterUnavailableDates() {
        return sitterUnavailableDateService.getAll();
    }

    @PostMapping
    public ApiResponse<SitterUnavailableDateDto> createSitterUnavailableDate(@RequestBody SitterUnavailableDateDto sitterUnavailableDateDto) {
        return sitterUnavailableDateService.create(sitterUnavailableDateDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<SitterUnavailableDateDto> updateSitterUnavailableDate(@PathVariable UUID id, @RequestBody SitterUnavailableDateDto sitterUnavailableDateDto) {
        return sitterUnavailableDateService.update(id, sitterUnavailableDateDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSitterUnavailableDate(@PathVariable UUID id) {
        return sitterUnavailableDateService.delete(id);
    }

}
