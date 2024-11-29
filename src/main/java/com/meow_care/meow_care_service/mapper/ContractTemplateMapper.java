package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.contract.ContractTemplateDto;
import com.meow_care.meow_care_service.dto.contract.ContractTemplateRequestDto;
import com.meow_care.meow_care_service.dto.contract.ContractTemplateResponseDto;
import com.meow_care.meow_care_service.entities.ContractTemplate;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContractTemplateMapper extends BaseMapper<ContractTemplateDto, ContractTemplate> {
    ContractTemplate toEntity(ContractTemplateRequestDto dto);

    ContractTemplateResponseDto toResponseDto(ContractTemplate entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(ContractTemplateRequestDto dto,@MappingTarget ContractTemplate entity);
}