package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.PetProfileDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.PetProfile;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.List;
import java.util.UUID;

public interface PetProfileService extends BaseService<PetProfileDto, PetProfile> {

    ApiResponse<List<PetProfileDto>> getProfileWithUserId(UUID id);

}
