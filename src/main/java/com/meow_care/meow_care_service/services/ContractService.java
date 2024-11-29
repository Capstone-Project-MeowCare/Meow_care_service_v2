package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.contract.ContractCreateRequestDto;
import com.meow_care.meow_care_service.dto.contract.ContractDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Contract;
import com.meow_care.meow_care_service.services.base.BaseService;

public interface ContractService extends BaseService<ContractDto, Contract> {
    ApiResponse<ContractDto> create(ContractCreateRequestDto contractCreateRequestDto);
}
