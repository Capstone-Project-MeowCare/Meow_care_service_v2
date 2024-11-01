package com.mewcare.meow_care_service.mapper;

import com.mewcare.meow_care_service.dto.OrderDetailDto;
import com.mewcare.meow_care_service.entities.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderDetailMapper extends BaseMapper<OrderDetailDto, OrderDetail> {
}