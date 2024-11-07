package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.PetProfileDto;
import com.meow_care.meow_care_service.entities.PetProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {MedicalConditionMapper.class})
public interface PetProfileMapper extends BaseMapper<PetProfileDto, PetProfile> {

}