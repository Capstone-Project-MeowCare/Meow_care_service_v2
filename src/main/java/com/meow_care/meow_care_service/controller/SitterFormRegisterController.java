package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.SitterFormRegisterDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.SitterFormRegisterService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/sitter-form-register")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class SitterFormRegisterController {

    private final SitterFormRegisterService sitterFormRegisterService;

    @GetMapping("/{id}")
    @Operation(summary = "Get Sitter Form Register by ID")
    public ApiResponse<SitterFormRegisterDto> getSitterFormRegisterById(@PathVariable UUID id) {
        return sitterFormRegisterService.get(id);
    }
    @GetMapping()
    @Operation(summary = "Get All Sitter Form Registers")
    public ApiResponse<List<SitterFormRegisterDto>> getAllSitterFormRegister() {
        return sitterFormRegisterService.getAll();
    }

    //get by user id
    @GetMapping("/user/{id}")
    @Operation(summary = "Get Sitter Form Register by User ID")
    public ApiResponse<SitterFormRegisterDto> getSitterFormRegisterByUserId(@PathVariable UUID id) {
        return sitterFormRegisterService.findByUserId(id);
    }

    //post
    @PostMapping
    @Operation(summary = "Create Sitter Form Register")
    public ApiResponse<SitterFormRegisterDto> createSitterFormRegister(@RequestBody SitterFormRegisterDto sitterFormRegisterDto) {
        return sitterFormRegisterService.create(sitterFormRegisterDto);
    }

    //put
    @PutMapping("/{id}")
    @Operation(summary = "Update Sitter Form Register")
    public ApiResponse<SitterFormRegisterDto> updateSitterFormRegister(@PathVariable UUID id, @RequestBody SitterFormRegisterDto sitterFormRegisterDto) {
        return sitterFormRegisterService.update(id, sitterFormRegisterDto);
    }

    //delete
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Sitter Form Register")
    public ApiResponse<Void> deleteSitterFormRegister(@PathVariable UUID id) {
        return sitterFormRegisterService.delete(id);
    }

}
