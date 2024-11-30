package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.withdraw.RequestWithdrawalDto;
import com.meow_care.meow_care_service.entities.RequestWithdrawal;
import com.meow_care.meow_care_service.entities.RequestWithdrawalCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestWithdrawalMapper extends BaseMapper<RequestWithdrawalDto, RequestWithdrawal> {
    RequestWithdrawal toEntity(RequestWithdrawalCreateDto dto);
}