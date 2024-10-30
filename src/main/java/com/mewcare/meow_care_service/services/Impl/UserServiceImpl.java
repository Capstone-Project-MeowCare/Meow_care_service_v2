package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.UserDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.entities.Role;
import com.mewcare.meow_care_service.entities.User;
import com.mewcare.meow_care_service.enums.ApiStatus;
import com.mewcare.meow_care_service.enums.RoleName;
import com.mewcare.meow_care_service.exception.ApiException;
import com.mewcare.meow_care_service.mapper.UserMapper;
import com.mewcare.meow_care_service.repositories.UserRepository;
import com.mewcare.meow_care_service.services.RoleService;
import com.mewcare.meow_care_service.services.UserService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserDto, User, UserRepository, UserMapper> implements UserService {

    private final RoleService roleService;

    public UserServiceImpl(UserRepository repository, UserMapper mapper, RoleService roleService) {
        super(repository, mapper);
        this.roleService = roleService;
    }

    @Override
    public ApiResponse<UserDto> create(UserDto dto) {
        User user = mapper.toEntity(dto);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user = repository.save(user);
        Role role = roleService.findEntityByName(RoleName.USER).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        user.setRoles(Collections.singleton(role));
        return ApiResponse.created(mapper.toDto(user));
    }

    @Override
    public void addRoleToUser(UUID userId, UUID roleId) {
        User user = repository.findById(userId).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        Role role = roleService.findEntityById(roleId).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        user.add(role);
        repository.save(user);
    }

    @Override
    public ApiResponse<Void> addRoleToUser(UUID userId, RoleName roleName) {
        User user = repository.findById(userId).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        Role role = roleService.findEntityByName(roleName).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        user.add(role);
        repository.save(user);
        return ApiResponse.success();
    }
}
