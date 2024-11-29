package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.contract.ContractTemplateDto;
import com.meow_care.meow_care_service.dto.contract.ContractTemplateRequestDto;
import com.meow_care.meow_care_service.dto.contract.ContractTemplateResponseDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.ContractTemplate;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.UUID;

public interface ContractTemplateService extends BaseService<ContractTemplateDto, ContractTemplate> {
    ApiResponse<ContractTemplateResponseDto> getContractTemplateResponse(UUID id);

    ApiResponse<ContractTemplateDto> findByName(String name);

    ApiResponse<ContractTemplateDto> create(ContractTemplateRequestDto dto);

    ApiResponse<ContractTemplateDto> update(UUID id, ContractTemplateRequestDto dto);
}
