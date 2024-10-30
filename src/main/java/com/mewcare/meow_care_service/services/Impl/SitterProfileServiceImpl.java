package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.SitterProfileDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.entities.Role;
import com.mewcare.meow_care_service.entities.SitterProfile;
import com.mewcare.meow_care_service.entities.User;
import com.mewcare.meow_care_service.enums.RoleName;
import com.mewcare.meow_care_service.mapper.SitterProfileMapper;
import com.mewcare.meow_care_service.repositories.SitterProfileRepository;
import com.mewcare.meow_care_service.services.RoleService;
import com.mewcare.meow_care_service.services.SitterProfileService;
import com.mewcare.meow_care_service.services.UserService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;
import com.mewcare.meow_care_service.util.UserUtils;
import org.springframework.stereotype.Service;

@Service
public class SitterProfileServiceImpl  extends BaseServiceImpl<SitterProfileDto, SitterProfile, SitterProfileRepository, SitterProfileMapper> implements SitterProfileService {

    private final UserService userService;
    private final RoleService roleService;

    public SitterProfileServiceImpl(SitterProfileRepository repository, SitterProfileMapper mapper, UserService userService, RoleService roleService) {
        super(repository, mapper);
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public ApiResponse<SitterProfileDto> create(SitterProfileDto dto) {

        User user= User.builder().id(UserUtils.getCurrentUserId()).build();
        SitterProfile sitterProfile = mapper.toEntity(dto);
        sitterProfile.setUser(user);
        sitterProfile = repository.save(sitterProfile);

        userService.addRoleToUser(user.getId(), RoleName.SITTER);

        return ApiResponse.created(mapper.toDto(sitterProfile));
    }
}
