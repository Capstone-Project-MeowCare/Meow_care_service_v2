package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.SitterProfileDto;
import com.meow_care.meow_care_service.dto.SitterProfileWithUserDto;
import com.meow_care.meow_care_service.entities.SitterProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class, ProfilePictureMapper.class})
public interface SitterProfileMapper extends BaseMapper<SitterProfileDto, SitterProfile> {

    @Override
    @Mapping(target = "sitterId", source = "user.id")
    @Mapping(target = "fullName", source = "user.fullName")
    @Mapping(target = "avatar", source = "user.avatar")
    SitterProfileDto toDto(SitterProfile entity);

    SitterProfile toEntity(SitterProfileWithUserDto sitterProfileWithUserDto);

    SitterProfileWithUserDto toDtoWithUser(SitterProfile sitterProfile);

}