package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.CertificateDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Certificate;
import com.meow_care.meow_care_service.mapper.CertificateMapper;
import com.meow_care.meow_care_service.repositories.CertificateRepository;
import com.meow_care.meow_care_service.services.CertificateService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CertificateServiceImpl extends BaseServiceImpl<CertificateDto, Certificate, CertificateRepository, CertificateMapper>
        implements CertificateService {
    public CertificateServiceImpl(CertificateRepository repository, CertificateMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ApiResponse<CertificateDto> create(CertificateDto dto) {
        return super.create(dto);
    }
}
