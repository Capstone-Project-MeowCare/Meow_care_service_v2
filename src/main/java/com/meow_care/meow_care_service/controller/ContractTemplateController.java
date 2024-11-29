package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.contract.ContractTemplateDto;
import com.meow_care.meow_care_service.dto.contract.ContractTemplateRequestDto;
import com.meow_care.meow_care_service.dto.contract.ContractTemplateResponseDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.ContractTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contract-templates")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class ContractTemplateController {

    private final ContractTemplateService contractTemplateService;

    @GetMapping("/{id}")
    public ApiResponse<ContractTemplateResponseDto> getContractTemplateById(@PathVariable UUID id) {
        return contractTemplateService.getContractTemplateResponse(id);
    }

    @GetMapping
    public ApiResponse<List<ContractTemplateDto>> getAllContractTemplates() {
        return contractTemplateService.getAll();
    }

    @GetMapping("/name")
    public ApiResponse<ContractTemplateDto> getContractTemplateByName(@RequestParam String name) {
        return contractTemplateService.findByName(name);
    }

    @PostMapping
    public ApiResponse<ContractTemplateDto> createContractTemplate(@RequestBody ContractTemplateRequestDto contractTemplateDto) {
        return contractTemplateService.create(contractTemplateDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<ContractTemplateDto> updateContractTemplate(@PathVariable UUID id, @RequestBody ContractTemplateRequestDto contractTemplateDto) {
        return contractTemplateService.update(id, contractTemplateDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteContractTemplate(@PathVariable UUID id) {
        return contractTemplateService.delete(id);
    }
}
