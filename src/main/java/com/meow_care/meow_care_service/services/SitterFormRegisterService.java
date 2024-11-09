package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.SitterFormRegisterDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.SitterFormRegister;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.UUID;

public interface SitterFormRegisterService extends BaseService<SitterFormRegisterDto, SitterFormRegister> {
    ApiResponse<SitterFormRegisterDto> findByUserId(UUID userId);
}
