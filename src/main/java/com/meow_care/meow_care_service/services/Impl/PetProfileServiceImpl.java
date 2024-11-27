package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.pet_profile.PetProfileDto;
import com.meow_care.meow_care_service.dto.pet_profile.PetProfileWithMedicalConditionDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.PetProfile;
import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.PetProfileMapper;
import com.meow_care.meow_care_service.repositories.PetProfileRepository;
import com.meow_care.meow_care_service.services.PetProfileService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.meow_care.meow_care_service.util.UserUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PetProfileServiceImpl extends BaseServiceImpl<PetProfileDto, PetProfile, PetProfileRepository, PetProfileMapper>
        implements PetProfileService {
    public PetProfileServiceImpl(PetProfileRepository repository, PetProfileMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ApiResponse<PetProfileDto> create(PetProfileDto dto) {
        PetProfile entity = mapper.toEntity(dto);
        entity.setUser(User.builder().id(UserUtils.getCurrentUserId()).build());
        entity = repository.save(entity);
        return ApiResponse.created(mapper.toDto(entity));
    }

    @Override
    public ApiResponse<List<PetProfileWithMedicalConditionDto>> getProfileWithUserId(UUID id) {
        List<PetProfile> petProfiles = repository.findByUserId(id);
        return ApiResponse.success(mapper.toPetProfileWithMedicalConditionDto(petProfiles));
    }

    @Override
    public ApiResponse<PetProfileWithMedicalConditionDto> getWithMedicalConditionById(UUID id) {
        PetProfile petProfile = repository.findById(id).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Pet profile not found")
        );
        return ApiResponse.success(mapper.toPetProfileWithMedicalConditionDto(petProfile));
    }

    @Override
    public ApiResponse<PetProfileWithMedicalConditionDto> createWithMedicalCondition(PetProfileWithMedicalConditionDto petProfileDto) {
        PetProfile entity = mapper.toEntityWithMedicalCondition(petProfileDto);
        entity.setUser(User.builder().id(UserUtils.getCurrentUserId()).build());
        entity = repository.save(entity);
        return ApiResponse.created(mapper.toPetProfileWithMedicalConditionDto(entity));
    }

    @Override
    public ApiResponse<PetProfileWithMedicalConditionDto> updateWithMedicalCondition(UUID id, PetProfileDto petProfileDto) {
        PetProfile petProfile = repository.findById(id).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Pet profile not found")
        );
        petProfile = mapper.updateWithMedicalCondition(petProfile, petProfileDto);
        petProfile = repository.save(petProfile);
        return ApiResponse.success(mapper.toPetProfileWithMedicalConditionDto(petProfile));
    }

    @Override
    public ApiResponse<List<PetProfileWithMedicalConditionDto>> getProfileWithTaskId(UUID id) {
        List<PetProfile> petProfiles = repository.findByTasksId(id);
        return ApiResponse.success(mapper.toPetProfileWithMedicalConditionDto(petProfiles));
    }
}
