package com.mewcare.meow_care_service.mapper;

import com.mewcare.meow_care_service.dto.ReactionDto;
import com.mewcare.meow_care_service.entities.Reaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReactionMapper extends BaseMapper<ReactionDto, Reaction> {
}