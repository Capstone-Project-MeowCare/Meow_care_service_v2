package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.user.UserDto;
import com.meow_care.meow_care_service.dto.user.UserWithRoleDto;
import com.meow_care.meow_care_service.dto.user.UserWithSitterProfileDto;
import com.meow_care.meow_care_service.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoleMapper.class, SitterProfileMapper.class})
public interface UserMapper extends BaseMapper<UserDto, User> {
    UserWithRoleDto toDtoWithRole(User user);

    @Override
    @Mapping(target = "password", ignore = true)
    UserDto toDto(User entity);

    UserWithSitterProfileDto toDtoWithSitterProfile(User entity);

    List<UserWithSitterProfileDto> toDtoWithSitterProfile(List<User> entities);

}