package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.SitterUnavailableDateDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.entities.SitterUnavailableDate;
import com.meow_care.meow_care_service.mapper.SitterUnavailableDateMapper;
import com.meow_care.meow_care_service.repositories.SitterUnavailableDateRepository;
import com.meow_care.meow_care_service.services.SitterProfileService;
import com.meow_care.meow_care_service.services.SitterUnavailableDateService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.meow_care.meow_care_service.util.UserUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SitterUnavailableDateServiceImpl extends BaseServiceImpl<SitterUnavailableDateDto, SitterUnavailableDate, SitterUnavailableDateRepository, SitterUnavailableDateMapper> implements SitterUnavailableDateService {

    private final SitterProfileService sitterProfileService;

    public SitterUnavailableDateServiceImpl(SitterUnavailableDateRepository repository, SitterUnavailableDateMapper mapper, SitterProfileService sitterProfileService) {
        super(repository, mapper);
        this.sitterProfileService = sitterProfileService;
    }

    @Override
    public SitterUnavailableDate createInternal(SitterUnavailableDate sitterUnavailableDate) {
        return repository.save(sitterUnavailableDate);
    }

    @Override
    public ApiResponse<SitterUnavailableDateDto> create(SitterUnavailableDateDto dto) {
        SitterUnavailableDate sitterUnavailableDate = mapper.toEntity(dto);

        SitterProfile sitterProfile = sitterProfileService.getEntityByUserId(UserUtils.getCurrentUserId()).orElseThrow(() -> new RuntimeException("Sitter profile not found"));

        sitterUnavailableDate.setSitterProfile(sitterProfile);
        sitterUnavailableDate = repository.save(sitterUnavailableDate);
        return ApiResponse.created(mapper.toDto(sitterUnavailableDate));
    }

    @Override
    public ResponseEntity<List<SitterUnavailableDateDto>> findBySitterId(UUID id) {
        List<SitterUnavailableDate> sitterUnavailableDate = repository.findBySitterProfile_User_Id(id);
        return ApiResponse.ok(mapper.toDtoList(sitterUnavailableDate));

    }
}
