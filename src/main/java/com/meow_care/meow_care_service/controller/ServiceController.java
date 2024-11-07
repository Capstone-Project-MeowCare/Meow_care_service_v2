package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.ServiceDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.ServiceEntityService;
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
@RequestMapping("/services")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class ServiceController {

    private final ServiceEntityService serviceEntityService;

    @GetMapping("/{id}")
    public ApiResponse<ServiceDto> getServiceById(@PathVariable UUID id) {
        return serviceEntityService.get(id);
    }

    @PostMapping
    public ApiResponse<ServiceDto> createService(@RequestBody ServiceDto serviceDto) {
        return serviceEntityService.create(serviceDto);
    }

    @GetMapping
    public ApiResponse<List<ServiceDto>> getAllServices() {
        return serviceEntityService.getAll();
    }

    @PutMapping("/{id}")
    public ApiResponse<ServiceDto> updateService(@PathVariable UUID id, @RequestBody ServiceDto serviceDto) {
        return serviceEntityService.update(id, serviceDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteService(@PathVariable UUID id) {
        return serviceEntityService.delete(id);
    }

}
