package com.mewcare.meow_care_service.mapper;

import com.mewcare.meow_care_service.dto.RoleDto;
import com.mewcare.meow_care_service.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper extends BaseMapper<RoleDto, Role> {
}
