package com.mewcare.meow_care_service.controller;

import com.mewcare.meow_care_service.dto.RoleDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.enums.RoleName;
import com.mewcare.meow_care_service.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;


    @GetMapping("/{id}")
    public ApiResponse<RoleDto> getRoleById(@PathVariable UUID id) {
        return roleService.get(id);
    }

    @GetMapping("/name/{name}")
    public ApiResponse<RoleDto> getRoleByName(@PathVariable RoleName name) {
        return roleService.findByName(name);
    }

    @GetMapping
    public ApiResponse<List<RoleDto>> getAllRoles() {
        return roleService.getAll();
    }
}
