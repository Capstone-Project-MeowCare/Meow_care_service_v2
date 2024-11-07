package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.UserDto;
import com.meow_care.meow_care_service.dto.UserWithRoleDto;
import com.meow_care.meow_care_service.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoleMapper.class})
public interface UserMapper extends BaseMapper<UserDto, User> {
    @Mapping(target = "password", ignore = true)
    UserWithRoleDto toDtoWithRole(User user);

    @Override
    @Mapping(target = "password", ignore = true)
    UserDto toDto(User entity);
}