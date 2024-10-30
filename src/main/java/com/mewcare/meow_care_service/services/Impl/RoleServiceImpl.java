package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.RoleDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.entities.Role;
import com.mewcare.meow_care_service.enums.ApiStatus;
import com.mewcare.meow_care_service.enums.RoleName;
import com.mewcare.meow_care_service.exception.ApiException;
import com.mewcare.meow_care_service.mapper.RoleMapper;
import com.mewcare.meow_care_service.repositories.RoleRepository;
import com.mewcare.meow_care_service.services.RoleService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleDto, Role, RoleRepository, RoleMapper> implements RoleService {
    public RoleServiceImpl(RoleRepository repository, RoleMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public Optional<Role> findEntityByName(RoleName name) {
        return repository.findByRoleName(name);
    }

    @Override
    public ApiResponse<RoleDto> findByName(RoleName name) {
        Role role = repository.findByRoleName(name).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        return ApiResponse.success(mapper.toDto(role));
    }
}
