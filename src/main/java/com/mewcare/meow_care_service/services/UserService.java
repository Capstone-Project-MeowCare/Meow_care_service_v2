package com.mewcare.meow_care_service.services;

import com.mewcare.meow_care_service.dto.UserDto;
import com.mewcare.meow_care_service.entities.User;
import com.mewcare.meow_care_service.enums.RoleName;
import com.mewcare.meow_care_service.services.base.BaseService;

import java.util.UUID;

public interface UserService  extends BaseService<UserDto, User> {
    void addRoleToUser(UUID userId, UUID roleId);

    void addRoleToUser(UUID userId, RoleName roleName);
}
