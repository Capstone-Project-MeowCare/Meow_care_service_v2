package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.UserDto;
import com.meow_care.meow_care_service.dto.UserWithRoleDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.enums.RoleName;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.UUID;

public interface UserService extends BaseService<UserDto, User> {
    //get user with roles
    ApiResponse<UserWithRoleDto> getUserWithRoles(UUID id);

    @SuppressWarnings("unused")
    void addRoleToUser(UUID userId, UUID roleId);

    ApiResponse<Void> addRoleToUser(UUID userId, RoleName roleName);

    ApiResponse<Long> countAllUsers();

    ApiResponse<Long> countAllUsersByRole(RoleName role);
}
