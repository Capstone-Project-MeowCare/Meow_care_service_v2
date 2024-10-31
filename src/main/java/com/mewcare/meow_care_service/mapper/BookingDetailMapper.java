package com.mewcare.meow_care_service.mapper;

import com.mewcare.meow_care_service.dto.BookingDetailDto;
import com.mewcare.meow_care_service.entities.BookingDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {PetProfileMapper.class, ServiceMapper.class})
public interface BookingDetailMapper extends BaseMapper<BookingDetailDto, BookingDetail> {

    @Override
    @Mapping(target = "pet.id", source = "petProfileId")
    @Mapping(target = "service.id", source = "serviceId")
    BookingDetail toEntity(BookingDetailDto dto);

}