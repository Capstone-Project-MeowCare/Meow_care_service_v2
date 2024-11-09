package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.UserDto;
import com.meow_care.meow_care_service.dto.UserWithRoleDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.enums.RoleName;
import com.meow_care.meow_care_service.services.UserService;
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
@RequestMapping("/users")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ApiResponse<UserWithRoleDto> getUserById(@PathVariable UUID id) {
        return userService.getUserWithRoles(id);
    }

    //count all and by role
    @GetMapping("/count")
    public ApiResponse<Long> countAllUsers() {
        return userService.countAllUsers();
    }

    @GetMapping("/count/{role}")
    public ApiResponse<Long> countAllUsersByRole(@PathVariable RoleName role) {
        return userService.countAllUsersByRole(role);
    }

    @PostMapping
    public ApiResponse<UserDto> createUser(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @PostMapping("/{userId}/roles")
    public ApiResponse<Void> addRoleToUser(@PathVariable UUID userId, @RequestParam RoleName roleName) {
        return userService.addRoleToUser(userId, roleName);
    }

    @GetMapping
    public ApiResponse<List<UserDto>> getAllUsers() {
        return userService.getAll();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserDto> updateUser(@PathVariable UUID id, @RequestBody UserDto userDto) {
        return userService.update(id, userDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable UUID id) {
        return userService.delete(id);
    }

}
