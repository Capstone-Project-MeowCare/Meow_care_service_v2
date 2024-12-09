package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.CertificateCreateRequest;
import com.meow_care.meow_care_service.dto.CertificateDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Certificate;
import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.CertificateMapper;
import com.meow_care.meow_care_service.repositories.CertificateRepository;
import com.meow_care.meow_care_service.repositories.SitterProfileRepository;
import com.meow_care.meow_care_service.services.CertificateService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CertificateServiceImpl extends BaseServiceImpl<CertificateDto, Certificate, CertificateRepository, CertificateMapper> implements CertificateService {

    private final SitterProfileRepository sitterProfileRepository;

    public CertificateServiceImpl(CertificateRepository repository, CertificateMapper mapper, SitterProfileRepository sitterProfileRepository) {
        super(repository, mapper);
        this.sitterProfileRepository = sitterProfileRepository;
    }

    @Override
    public ApiResponse<CertificateDto> create(CertificateCreateRequest certificateCreateRequest) {

        SitterProfile sitterProfile = sitterProfileRepository.findByUserId(certificateCreateRequest.userId())
                .orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND, "Sitter profile not found"));

        Certificate certificate = mapper.toEntity(certificateCreateRequest);
        certificate.setSitterProfile(sitterProfile);
        repository.save(certificate);
        return ApiResponse.success(mapper.toDto(certificate));
    }

    @Override
    public ApiResponse<List<CertificateDto>> getBySitterProfileId(UUID id) {
        List<Certificate> certificates = repository.findBySitterProfileId(id);

        return ApiResponse.success(mapper.toDtoList(certificates));
    }

    @Override
    public ApiResponse<List<CertificateDto>> getByUserId(UUID id) {
        List<Certificate> certificates = repository.findBySitterProfileUserId(id);

        return ApiResponse.success(mapper.toDtoList(certificates));
    }
}
