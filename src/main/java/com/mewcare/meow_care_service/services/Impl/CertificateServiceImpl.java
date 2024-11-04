package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.CertificateDto;
import com.mewcare.meow_care_service.entities.Certificate;
import com.mewcare.meow_care_service.mapper.CertificateMapper;
import com.mewcare.meow_care_service.repositories.CertificateRepository;
import com.mewcare.meow_care_service.services.CertificateService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CertificateServiceImpl extends BaseServiceImpl<CertificateDto, Certificate, CertificateRepository, CertificateMapper>
        implements CertificateService {
    public CertificateServiceImpl(CertificateRepository repository, CertificateMapper mapper) {
        super(repository, mapper);
    }
}
