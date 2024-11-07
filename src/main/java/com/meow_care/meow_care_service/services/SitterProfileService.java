package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.SitterProfileDto;
import com.meow_care.meow_care_service.dto.SitterProfileWithUserDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.UUID;

public interface SitterProfileService extends BaseService<SitterProfileDto, SitterProfile> {

    ApiResponse<SitterProfileWithUserDto> getProfileWithUser(UUID id);

}
