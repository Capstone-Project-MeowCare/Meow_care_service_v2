package com.mewcare.meow_care_service.mapper;

import com.mewcare.meow_care_service.dto.RefundDto;
import com.mewcare.meow_care_service.entities.Refund;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RefundMapper extends BaseMapper<RefundDto, Refund> {
}