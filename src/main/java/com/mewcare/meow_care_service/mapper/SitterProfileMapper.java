package com.mewcare.meow_care_service.mapper;

import com.mewcare.meow_care_service.dto.SitterProfileDto;
import com.mewcare.meow_care_service.dto.UserDto;
import com.mewcare.meow_care_service.dto.UserWithRoleDto;
import com.mewcare.meow_care_service.entities.SitterProfile;
import com.mewcare.meow_care_service.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoleMapper.class})
public interface SitterProfileMapper extends BaseMapper<SitterProfileDto, SitterProfile> {
}