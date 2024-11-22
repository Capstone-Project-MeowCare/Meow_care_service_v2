package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.UserQuizResultDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.UserQuizResult;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.UUID;

public interface UserQuizResultService extends BaseService<UserQuizResultDto, UserQuizResult> {
    ApiResponse<UserQuizResultDto> create(UUID userId, UserQuizResultDto userQuizResultDto);
}
