package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.SitterUnavailableDateDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.entities.SitterUnavailableDate;
import com.mewcare.meow_care_service.entities.User;
import com.mewcare.meow_care_service.mapper.SitterUnavailableDateMapper;
import com.mewcare.meow_care_service.repositories.SitterUnavailableDateRepository;
import com.mewcare.meow_care_service.services.SitterUnavailableDateService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;
import com.mewcare.meow_care_service.util.UserUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SitterUnavailableDateServiceImpl extends BaseServiceImpl<SitterUnavailableDateDto, SitterUnavailableDate, SitterUnavailableDateRepository, SitterUnavailableDateMapper>
        implements SitterUnavailableDateService {
    public SitterUnavailableDateServiceImpl(SitterUnavailableDateRepository repository, SitterUnavailableDateMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ApiResponse<SitterUnavailableDateDto> create(SitterUnavailableDateDto dto) {
        SitterUnavailableDate sitterUnavailableDate = mapper.toEntity(dto);
        sitterUnavailableDate.setSitter(User.builder().id(UserUtils.getCurrentUserId()).build());
        sitterUnavailableDate = repository.save(sitterUnavailableDate);
        return ApiResponse.created(mapper.toDto(sitterUnavailableDate));
    }

    @Override
    public ResponseEntity<List<SitterUnavailableDateDto>> findBySitterId(UUID id) {
        List<SitterUnavailableDate> sitterUnavailableDate = repository.findBySitterId(id);
        return ApiResponse.ok(mapper.toDtoList(sitterUnavailableDate));

    }
}
