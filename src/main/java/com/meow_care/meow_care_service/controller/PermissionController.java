package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.PermissionDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.PermissionService;
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
@RequestMapping("/permissions")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping("/{id}")
    public ApiResponse<PermissionDto> getPermissionById(@PathVariable UUID id) {
        return permissionService.get(id);
    }

    @GetMapping
    public ApiResponse<List<PermissionDto>> getAllPermissions() {
        return permissionService.getAll();
    }

    @PostMapping
    public ApiResponse<PermissionDto> createPermission(@RequestBody PermissionDto permissionDto) {
        return permissionService.create(permissionDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<PermissionDto> updatePermission(@PathVariable UUID id, @RequestBody PermissionDto permissionDto) {
        return permissionService.update(id, permissionDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePermission(@PathVariable UUID id) {
        return permissionService.delete(id);
    }

}
