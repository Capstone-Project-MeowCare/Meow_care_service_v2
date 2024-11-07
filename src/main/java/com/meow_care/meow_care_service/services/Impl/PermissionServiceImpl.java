package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.PermissionDto;
import com.meow_care.meow_care_service.entities.Permission;
import com.meow_care.meow_care_service.mapper.PermissionMapper;
import com.meow_care.meow_care_service.repositories.PermissionRepository;
import com.meow_care.meow_care_service.services.PermissionService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionDto, Permission, PermissionRepository, PermissionMapper>
        implements PermissionService {
    public PermissionServiceImpl(PermissionRepository repository, PermissionMapper mapper) {
        super(repository, mapper);
    }
}
