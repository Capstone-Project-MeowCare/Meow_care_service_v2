package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.ContractDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.ContractService;
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
@RequestMapping("/contracts")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class ContractController {

    private final ContractService contractService;

    @GetMapping("/{id}")
    public ApiResponse<ContractDto> getContractById(@PathVariable UUID id) {
        return contractService.get(id);
    }

    @GetMapping
    public ApiResponse<List<ContractDto>> getAllContracts() {
        return contractService.getAll();
    }

    @PostMapping
    public ApiResponse<ContractDto> createContract(@RequestBody ContractDto contractDto) {
        return contractService.create(contractDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<ContractDto> updateContract(@PathVariable UUID id, @RequestBody ContractDto contractDto) {
        return contractService.update(id, contractDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteContract(@PathVariable UUID id) {
        return contractService.delete(id);
    }
}
