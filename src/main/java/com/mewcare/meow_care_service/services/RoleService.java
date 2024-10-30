package com.mewcare.meow_care_service.services;

import com.mewcare.meow_care_service.dto.RoleDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.entities.Role;
import com.mewcare.meow_care_service.enums.RoleName;
import com.mewcare.meow_care_service.services.base.BaseService;

import java.util.Optional;

public interface RoleService  extends BaseService<RoleDto, Role> {

    Optional<Role> findEntityByName(RoleName name);

    ApiResponse<RoleDto> findByName(RoleName name);

}
