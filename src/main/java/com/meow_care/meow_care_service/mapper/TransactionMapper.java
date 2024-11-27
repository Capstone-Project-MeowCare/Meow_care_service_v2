package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.TransactionDto;
import com.meow_care.meow_care_service.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper extends BaseMapper<TransactionDto, Transaction> {
    @Override
    @Mapping(target = "fromUserId", source = "fromUser.id")
    @Mapping(target = "toUserId", source = "toUser.id")
    @Mapping(target = "fromUserEmail", source = "fromUser.email")
    @Mapping(target = "toUserEmail", source = "toUser.email")
    TransactionDto toDto(Transaction entity);

}