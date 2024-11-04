package com.mewcare.meow_care_service.mapper;

import com.mewcare.meow_care_service.dto.PetProfileDto;
import com.mewcare.meow_care_service.entities.PetProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {MedicalConditionMapper.class})
public interface PetProfileMapper extends BaseMapper<PetProfileDto, PetProfile> {

}