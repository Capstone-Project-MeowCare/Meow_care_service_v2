package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.pet_profile.PetProfileDto;
import com.meow_care.meow_care_service.dto.pet_profile.PetProfileWithMedicalConditionDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.PetProfileService;
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
@RequestMapping("/pet-profiles")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class PetProfileController {

    private final PetProfileService petProfileService;

    @GetMapping("/{id}")
    public ApiResponse<PetProfileWithMedicalConditionDto> getPetProfileById(@PathVariable UUID id) {
        return petProfileService.getWithMedicalConditionById(id);
    }

    @GetMapping
    public ApiResponse<List<PetProfileDto>> getAllPetProfiles() {
        return petProfileService.getAll();
    }

    @GetMapping("/user/{id}")
    public ApiResponse<List<PetProfileWithMedicalConditionDto>> getPetProfileByUserId(@PathVariable UUID id) {
        return petProfileService.getProfileWithUserId(id);
    }

    //get by task id
    @GetMapping("/task/{id}")
    public ApiResponse<List<PetProfileWithMedicalConditionDto>> getPetProfileByTaskId(@PathVariable UUID id) {
        return petProfileService.getProfileWithTaskId(id);
    }

    @PostMapping
    public ApiResponse<PetProfileWithMedicalConditionDto> createPetProfile(@RequestBody PetProfileWithMedicalConditionDto petProfileDto) {
        return petProfileService.createWithMedicalCondition(petProfileDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<PetProfileDto> updatePetProfile(@PathVariable UUID id, @RequestBody PetProfileDto petProfileDto) {
        return petProfileService.update(id, petProfileDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePetProfile(@PathVariable UUID id) {
        return petProfileService.delete(id);
    }
}
