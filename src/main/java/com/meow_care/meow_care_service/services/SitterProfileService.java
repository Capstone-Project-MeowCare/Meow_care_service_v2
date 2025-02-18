package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.ProfilePictureDto;
import com.meow_care.meow_care_service.dto.SitterProfileDto;
import com.meow_care.meow_care_service.dto.SitterProfileWithUserDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.enums.ServiceType;
import com.meow_care.meow_care_service.enums.SitterProfileStatus;
import com.meow_care.meow_care_service.services.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SitterProfileService extends BaseService<SitterProfileDto, SitterProfile> {

    ApiResponse<SitterProfileWithUserDto> getProfileWithUser(UUID id);

    Optional<SitterProfile> getEntityByUserId(UUID id);

    SitterProfile getEntityBySitterId(UUID sitterId);
    //get by sitter id
    ApiResponse<SitterProfileDto> getBySitterId(UUID id);

    ApiResponse<List<SitterProfileDto>> getAllByStatus(SitterProfileStatus status);

    ApiResponse<Void> updateStatusById(SitterProfileStatus status, UUID id);

    SitterProfile updateRating(UUID id, double rating);

    ApiResponse<Page<SitterProfileDto>> search(Double latitude, Double longitude, ServiceType serviceType, LocalDate startTime, LocalDate endTime, BigDecimal minPrice, BigDecimal maxPrice, Integer minQuantity, Pageable pageable);

    ApiResponse<SitterProfileDto> addProfilePictures(UUID id, List<ProfilePictureDto> pictureDtos);

    ApiResponse<SitterProfileDto> removeProfilePicture(UUID id, List<ProfilePictureDto> pictureDtos);
}
