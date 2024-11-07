package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.SitterProfileDto;
import com.meow_care.meow_care_service.dto.SitterProfileWithUserDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.SitterProfileService;
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
@RequestMapping("/sitter-profiles")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class SitterProfileController {

    private final SitterProfileService sitterProfileService;

    @GetMapping("/{id}")
    public ApiResponse<SitterProfileWithUserDto> getSitterProfileById(@PathVariable UUID id) {
        return sitterProfileService.getProfileWithUser(id);
    }

    @PostMapping
    public ApiResponse<SitterProfileDto> createSitterProfile(@RequestBody SitterProfileDto sitterProfileDto) {
        return sitterProfileService.create(sitterProfileDto);
    }

    @GetMapping
    public ApiResponse<List<SitterProfileDto>> getAllSitterProfiles() {
        return sitterProfileService.getAll();
    }

    @PutMapping("/{id}")
    public ApiResponse<SitterProfileDto> updateSitterProfile(@PathVariable UUID id, @RequestBody SitterProfileDto sitterProfileDto) {
        return sitterProfileService.update(id, sitterProfileDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSitterProfile(@PathVariable UUID id) {
        return sitterProfileService.delete(id);
    }

}
