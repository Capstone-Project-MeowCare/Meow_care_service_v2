package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.SitterProfileDto;
import com.meow_care.meow_care_service.dto.SitterProfileWithUserDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.enums.SitterProfileStatus;
import com.meow_care.meow_care_service.services.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SitterProfileService extends BaseService<SitterProfileDto, SitterProfile> {

    ApiResponse<SitterProfileWithUserDto> getProfileWithUser(UUID id);

    Optional<SitterProfile> getEntityByUserId(UUID id);

    //get by sitter id
    ApiResponse<SitterProfileDto> getBySitterId(UUID id);

    ApiResponse<List<SitterProfileDto>> getAllByStatus(SitterProfileStatus status);

    ApiResponse<Void> updateStatusById(SitterProfileStatus status, UUID id);

    ApiResponse<Page<SitterProfileDto>> findAllWithDistance(double latitude, double longitude, Pageable pageable);

    //query by latitude and longitude
    ApiResponse<Page<SitterProfileDto>> findAllOrderByDistance(double latitude, double longitude, Pageable pageable);
}
