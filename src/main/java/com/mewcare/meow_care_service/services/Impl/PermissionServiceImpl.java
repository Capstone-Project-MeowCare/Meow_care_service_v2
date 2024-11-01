package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.PermissionDto;
import com.mewcare.meow_care_service.entities.Permission;
import com.mewcare.meow_care_service.mapper.PermissionMapper;
import com.mewcare.meow_care_service.repositories.PermissionRepository;
import com.mewcare.meow_care_service.services.PermissionService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;

public class PermissionServiceImpl extends BaseServiceImpl<PermissionDto, Permission, PermissionRepository, PermissionMapper>
        implements PermissionService {
    public PermissionServiceImpl(PermissionRepository repository, PermissionMapper mapper) {
        super(repository, mapper);
    }
}
