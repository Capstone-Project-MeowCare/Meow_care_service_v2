package com.mewcare.meow_care_service.services;

import com.mewcare.meow_care_service.dto.SitterUnavailableDateDto;
import com.mewcare.meow_care_service.entities.SitterUnavailableDate;
import com.mewcare.meow_care_service.services.base.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface SitterUnavailableDateService extends BaseService<SitterUnavailableDateDto, SitterUnavailableDate> {

    //find by sitter id
    ResponseEntity<List<SitterUnavailableDateDto>> findBySitterId(UUID id);

}
