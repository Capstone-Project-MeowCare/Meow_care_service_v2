package com.mewcare.meow_care_service.controller;

import com.mewcare.meow_care_service.dto.ServiceTypeDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.services.ServiceTypeService;
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
@RequestMapping("/api/service-types")
@RequiredArgsConstructor
public class ServiceTypeController {

    private final ServiceTypeService serviceTypeService;

    @GetMapping("/{id}")
    public ApiResponse<ServiceTypeDto> getServiceTypeById(@PathVariable UUID id) {
        return serviceTypeService.get(id);
    }

    @PostMapping
    public ApiResponse<ServiceTypeDto> createServiceType(@RequestBody ServiceTypeDto serviceTypeDto) {
        return serviceTypeService.create(serviceTypeDto);
    }

    @GetMapping
    public ApiResponse<List<ServiceTypeDto>> getAllServiceTypes() {
        return serviceTypeService.getAll();
    }

    @PutMapping("/{id}")
    public ApiResponse<ServiceTypeDto> updateServiceType(@PathVariable UUID id, @RequestBody ServiceTypeDto serviceTypeDto) {
        return serviceTypeService.update(id, serviceTypeDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteServiceType(@PathVariable UUID id) {
        return serviceTypeService.delete(id);
    }
}
