package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.CertificateDto;
import com.meow_care.meow_care_service.entities.Certificate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CertificateMapper extends BaseMapper<CertificateDto, Certificate> {
}