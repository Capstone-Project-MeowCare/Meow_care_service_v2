package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.ServiceDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.enums.ServiceStatus;
import com.meow_care.meow_care_service.enums.ServiceType;
import com.meow_care.meow_care_service.services.ServiceEntityService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class ServiceController {

    private final ServiceEntityService serviceEntityService;

    @Operation(summary = "Get service by id")
    @GetMapping("/{id}")
    public ApiResponse<ServiceDto> getServiceById(@PathVariable UUID id) {
        return serviceEntityService.get(id);
    }

    //get by service type
    @Operation(summary = "Get services by service type")
    @GetMapping("/serviceType")
    public ApiResponse<List<ServiceDto>> getServiceByServiceType(@RequestParam ServiceType serviceType) {
        return serviceEntityService.getByServiceType(serviceType);
    }

    //get by service type and status
    @Operation(summary = "Get services by service type and status")
    @GetMapping("/serviceType/status")
    public ApiResponse<List<ServiceDto>> getServiceByServiceType(@RequestParam ServiceType serviceType, @RequestParam ServiceStatus status) {
        return serviceEntityService.getByServiceType(serviceType, status);
    }

    //get by sitter id
    @Operation(summary = "Get services by sitter id")
    @GetMapping("/sitter/{id}")
    public ApiResponse<List<ServiceDto>> getServiceBySitterId(@PathVariable UUID id) {
        return serviceEntityService.getBySitterId(id);
    }

    @Operation(summary = "Get services by sitter id and service type id")
    @GetMapping("/sitter/{id}/type")
    public ApiResponse<List<ServiceDto>> getServiceBySitterId(@RequestParam ServiceType serviceType, @RequestParam(required = false) ServiceStatus status, @PathVariable UUID id) {
        return serviceEntityService.getBySitterId(id, serviceType, status);
    }

    @Operation(summary = "Create a new service")
    @PostMapping
    public ApiResponse<ServiceDto> createService(@RequestBody ServiceDto serviceDto) {
        return serviceEntityService.create(serviceDto);
    }

    @Operation(summary = "Get all services")
    @GetMapping
    public ApiResponse<List<ServiceDto>> getAllServices() {
        return serviceEntityService.getAll();
    }

    @Operation(summary = "Update service by id")
    @PutMapping("/{id}")
    public ApiResponse<ServiceDto> updateService(@PathVariable UUID id, @RequestBody ServiceDto serviceDto) {
        return serviceEntityService.update(id, serviceDto);
    }

    //update status by id
    @Operation(summary = "Update service status by id")
    @PutMapping("/{id}/status")
    public ApiResponse<Void> updateServiceStatus(@PathVariable UUID id, @RequestParam ServiceStatus status) {
        return serviceEntityService.updateStatusById(status, id);
    }

    @Operation(summary = "Delete service by id")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteService(@PathVariable UUID id) {
        return serviceEntityService.softDeleteService(id);
    }

}
