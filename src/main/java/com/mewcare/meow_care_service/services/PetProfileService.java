package com.mewcare.meow_care_service.services;

import com.mewcare.meow_care_service.dto.PetProfileDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.entities.PetProfile;
import com.mewcare.meow_care_service.services.base.BaseService;

import java.util.UUID;

public interface PetProfileService extends BaseService<PetProfileDto, PetProfile> {

    ApiResponse<PetProfileDto> getProfileWithUserId(UUID id);

}
