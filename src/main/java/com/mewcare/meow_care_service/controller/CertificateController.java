package com.mewcare.meow_care_service.controller;

import com.mewcare.meow_care_service.dto.CertificateDto;
import com.mewcare.meow_care_service.dto.response.ApiResponse;
import com.mewcare.meow_care_service.services.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class CertificateController {

    private final CertificateService certificateService;

    @GetMapping("/{id}")
    public ApiResponse<CertificateDto> getCertificateById(@PathVariable UUID id) {
        return certificateService.get(id);
    }

    @GetMapping
    public ApiResponse<List<CertificateDto>> getAllCertificates() {
        return certificateService.getAll();
    }

    @PostMapping
    public ApiResponse<CertificateDto> createCertificate(@RequestBody CertificateDto certificateDto) {
        return certificateService.create(certificateDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<CertificateDto> updateCertificate(@PathVariable UUID id, @RequestBody CertificateDto certificateDto) {
        return certificateService.update(id, certificateDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCertificate(@PathVariable UUID id) {
        return certificateService.delete(id);
    }
}
