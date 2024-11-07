package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.PetItemDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.PetItemService;
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
@RequestMapping("/pet-items")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class PetItemController {
    private final PetItemService petItemService;

    @GetMapping("/{id}")
    public ApiResponse<PetItemDto> getPetItemById(@PathVariable UUID id) {
        return petItemService.get(id);
    }

    @GetMapping
    public ApiResponse<List<PetItemDto>> getAllPetItems() {
        return petItemService.getAll();
    }

    @PostMapping
    public ApiResponse<PetItemDto> createPetItem(@RequestBody PetItemDto petItemDto) {
        return petItemService.create(petItemDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<PetItemDto> updatePetItem(@PathVariable UUID id, @RequestBody PetItemDto petItemDto) {
        return petItemService.update(id, petItemDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePetItem(@PathVariable UUID id) {
        return petItemService.delete(id);
    }
}
