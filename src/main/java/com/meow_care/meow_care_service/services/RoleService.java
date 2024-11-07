package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.RoleDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Role;
import com.meow_care.meow_care_service.enums.RoleName;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.Optional;

public interface RoleService extends BaseService<RoleDto, Role> {

    Optional<Role> findEntityByName(RoleName name);

    ApiResponse<RoleDto> findByName(RoleName name);

}
