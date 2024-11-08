package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.ProfilePictureDto;
import com.meow_care.meow_care_service.entities.ProfilePicture;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfilePictureMapper extends BaseMapper<ProfilePictureDto, ProfilePicture> {
}