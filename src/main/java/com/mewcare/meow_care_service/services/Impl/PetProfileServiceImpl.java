package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.PetProfileDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.entities.PetProfile;
import com.mewcare.meow_care_service.entities.User;
import com.mewcare.meow_care_service.mapper.PetProfileMapper;
import com.mewcare.meow_care_service.repositories.PetProfileRepository;
import com.mewcare.meow_care_service.services.PetProfileService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;
import com.mewcare.meow_care_service.util.UserUtils;
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
    public ApiResponse<List<PetProfileDto>> getProfileWithUserId(UUID id) {
        List<PetProfile> petProfiles = repository.findByUserId(id);
        return ApiResponse.success(mapper.toDtoList(petProfiles));
    }
}
