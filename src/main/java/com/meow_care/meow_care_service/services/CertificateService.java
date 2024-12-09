package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.CertificateCreateRequest;
import com.meow_care.meow_care_service.dto.CertificateDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Certificate;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.List;
import java.util.UUID;

public interface CertificateService extends BaseService<CertificateDto, Certificate> {
    ApiResponse<CertificateDto> create(CertificateCreateRequest certificateCreateRequest);

    ApiResponse<List<CertificateDto>> getBySitterProfileId(UUID id);

    ApiResponse<List<CertificateDto>> getByUserId(UUID id);
}
