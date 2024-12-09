package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.SitterFormRegisterDto;
import com.meow_care.meow_care_service.entities.SitterFormRegister;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SitterFormRegisterMapper extends BaseMapper<SitterFormRegisterDto, SitterFormRegister> {

    @Override
    @Mapping(target = "user.id", source = "userId")
    SitterFormRegister toEntity(SitterFormRegisterDto dto);

    @Override
    @Mapping(target = "userId", source = "user.id")
    SitterFormRegisterDto toDto(SitterFormRegister entity);

    @AfterMapping
    default void linkCertificates(@MappingTarget SitterFormRegister sitterFormRegister) {
        if (sitterFormRegister.getCertificates() == null) {
            return;
        }
        sitterFormRegister.getCertificates().forEach(certificate -> certificate.setSitterFormRegister(sitterFormRegister));
    }
}