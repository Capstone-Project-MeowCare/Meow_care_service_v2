package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.SitterUnavailableDateDto;
import com.meow_care.meow_care_service.entities.SitterUnavailableDate;
import com.meow_care.meow_care_service.services.base.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface SitterUnavailableDateService extends BaseService<SitterUnavailableDateDto, SitterUnavailableDate> {

    //find by sitter id
    ResponseEntity<List<SitterUnavailableDateDto>> findBySitterId(UUID id);

}
