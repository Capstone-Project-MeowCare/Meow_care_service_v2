package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.PermissionDto;
import com.meow_care.meow_care_service.entities.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PermissionMapper extends BaseMapper<PermissionDto, Permission> {
}
