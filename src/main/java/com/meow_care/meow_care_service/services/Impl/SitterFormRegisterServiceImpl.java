package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.SitterFormRegisterDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.SitterFormRegister;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.SitterFormRegisterStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.SitterFormRegisterMapper;
import com.meow_care.meow_care_service.repositories.SitterFormRegisterRepository;
import com.meow_care.meow_care_service.services.SitterFormRegisterService;
import com.meow_care.meow_care_service.services.SitterProfileService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SitterFormRegisterServiceImpl extends BaseServiceImpl<SitterFormRegisterDto, SitterFormRegister, SitterFormRegisterRepository, SitterFormRegisterMapper> implements SitterFormRegisterService {

    private final SitterProfileService sitterProfileService;

    public SitterFormRegisterServiceImpl(SitterFormRegisterRepository repository, SitterFormRegisterMapper mapper, SitterProfileService sitterProfileService) {
        super(repository, mapper);
        this.sitterProfileService = sitterProfileService;
    }

    @Override
    public ApiResponse<SitterFormRegisterDto> create(SitterFormRegisterDto dto) {
        SitterFormRegister sitterFormRegister = mapper.toEntity(dto);
        sitterFormRegister.setStatus(SitterFormRegisterStatus.PENDING);
        sitterFormRegister = repository.save(sitterFormRegister);
        return ApiResponse.success(mapper.toDto(sitterFormRegister));
    }

    @Override
    public ApiResponse<SitterFormRegisterDto> findByUserId(UUID userId) {
        SitterFormRegister sitterFormRegister = repository.findByUserId(userId).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND, "Sitter form register not found"));
        return ApiResponse.success(mapper.toDto(sitterFormRegister));
    }

    @Override
    public ApiResponse<SitterFormRegisterDto> update(UUID id, SitterFormRegisterDto dto) {
        SitterFormRegister sitterFormRegister = repository.findById(id).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND)
        );

        mapper.partialUpdate(dto, sitterFormRegister);
        return ApiResponse.updated(mapper.toDto(sitterFormRegister));
    }
}
