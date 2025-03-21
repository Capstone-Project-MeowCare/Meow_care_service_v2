package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.pet_profile.PetProfileDto;
import com.meow_care.meow_care_service.dto.pet_profile.PetProfileWithMedicalConditionDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.PetProfile;
import com.meow_care.meow_care_service.enums.PetProfileStatus;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.List;
import java.util.UUID;

public interface PetProfileService extends BaseService<PetProfileDto, PetProfile> {

    ApiResponse<List<PetProfileWithMedicalConditionDto>> getProfileWithUserId(UUID id);

    ApiResponse<PetProfileWithMedicalConditionDto> getWithMedicalConditionById(UUID id);

    ApiResponse<PetProfileWithMedicalConditionDto> createWithMedicalCondition(PetProfileWithMedicalConditionDto petProfileDto);

    ApiResponse<List<PetProfileWithMedicalConditionDto>> getProfileWithTaskId(UUID id);

    int updateStatusInternal(UUID id, PetProfileStatus status);
}
