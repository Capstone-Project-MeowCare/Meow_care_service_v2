package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.MedicalConditionDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.MedicalConditionService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/medical-conditions")
@RequiredArgsConstructor
public class MedicalConditionController {

    private final MedicalConditionService medicalConditionService;

    @GetMapping("/{id}")
    public ApiResponse<MedicalConditionDto> getMedicalConditionById(@PathVariable UUID id) {
        return medicalConditionService.get(id);
    }

    @PostMapping
    public ApiResponse<MedicalConditionDto> createMedicalCondition(@RequestBody MedicalConditionDto medicalConditionDto) {
        return medicalConditionService.create(medicalConditionDto);
    }

    @GetMapping
    public ApiResponse<List<MedicalConditionDto>> getAllMedicalConditions() {
        return medicalConditionService.getAll();
    }

    @PutMapping("/{id}")
    public ApiResponse<MedicalConditionDto> updateMedicalCondition(@PathVariable UUID id, @RequestBody MedicalConditionDto medicalConditionDto) {
        return medicalConditionService.update(id, medicalConditionDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteMedicalCondition(@PathVariable UUID id) {
        return medicalConditionService.delete(id);
    }
}
