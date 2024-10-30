package com.mewcare.meow_care_service.controller;

import com.mewcare.meow_care_service.dto.PetProfileDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.services.PetProfileService;
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
@RequestMapping("/api/pet-profiles")
@RequiredArgsConstructor
public class PetProfileController {

    private final PetProfileService petProfileService;

    @GetMapping("/{id}")
    public ApiResponse<PetProfileDto> getPetProfileById(@PathVariable UUID id) {
        return petProfileService.get(id);
    }

    @PostMapping
    public ApiResponse<PetProfileDto> createPetProfile(@RequestBody PetProfileDto petProfileDto) {
        return petProfileService.create(petProfileDto);
    }

    @GetMapping
    public ApiResponse<List<PetProfileDto>> getAllPetProfiles() {
        return petProfileService.getAll();
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
