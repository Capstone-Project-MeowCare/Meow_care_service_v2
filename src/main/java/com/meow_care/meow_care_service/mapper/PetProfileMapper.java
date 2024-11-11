package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.pet_profile.PetProfileDto;
import com.meow_care.meow_care_service.dto.pet_profile.PetProfileWithMedicalConditionDto;
import com.meow_care.meow_care_service.entities.PetProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {MedicalConditionMapper.class})
public interface PetProfileMapper extends BaseMapper<PetProfileDto, PetProfile> {

    PetProfile toEntityWithMedicalCondition(PetProfileWithMedicalConditionDto petProfileDto);

    PetProfileWithMedicalConditionDto toPetProfileWithMedicalConditionDto(PetProfile petProfile);

    List<PetProfileWithMedicalConditionDto> toPetProfileWithMedicalConditionDto(List<PetProfile> petProfiles);

}