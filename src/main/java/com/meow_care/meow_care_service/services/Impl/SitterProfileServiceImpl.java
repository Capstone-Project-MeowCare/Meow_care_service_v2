package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.SitterProfileDto;
import com.meow_care.meow_care_service.dto.SitterProfileWithUserDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.SitterProfileMapper;
import com.meow_care.meow_care_service.repositories.SitterProfileRepository;
import com.meow_care.meow_care_service.services.SitterProfileService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.meow_care.meow_care_service.util.UserUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SitterProfileServiceImpl extends BaseServiceImpl<SitterProfileDto, SitterProfile, SitterProfileRepository, SitterProfileMapper> implements SitterProfileService {

    public SitterProfileServiceImpl(SitterProfileRepository repository, SitterProfileMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ApiResponse<SitterProfileDto> create(SitterProfileDto dto) {
        User user = User.builder().id(UserUtils.getCurrentUserId()).build();
        SitterProfile sitterProfile = mapper.toEntity(dto);
        sitterProfile.setUser(user);
        sitterProfile = repository.save(sitterProfile);
        return ApiResponse.created(mapper.toDto(sitterProfile));
    }

    @Override
    public ApiResponse<SitterProfileWithUserDto> getProfileWithUser(UUID id) {
        SitterProfile sitterProfile = repository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        return ApiResponse.success(mapper.toDtoWithUser(sitterProfile));
    }

    @Override
    public ApiResponse<SitterProfileDto> getBySitterId(UUID id) {
        SitterProfile sitterProfile = repository.findByUserId(id).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        return ApiResponse.success(mapper.toDto(sitterProfile));
    }
}
