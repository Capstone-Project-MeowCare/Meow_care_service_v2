package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.BookingDetailDto;
import com.meow_care.meow_care_service.dto.BookingDetailWithPetAndServiceDto;
import com.meow_care.meow_care_service.entities.BookingDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {PetProfileMapper.class, ServiceMapper.class})
public interface BookingDetailMapper extends BaseMapper<BookingDetailDto, BookingDetail> {

    @Override
    @Mapping(target = "pet.id", source = "petProfileId")
    @Mapping(target = "service.id", source = "serviceId")
    BookingDetail toEntity(BookingDetailDto dto);

    //to dto with pet and service
    BookingDetailWithPetAndServiceDto toDtoWithPetAndService(BookingDetail bookingDetail);

    List<BookingDetail> toEntity(List<BookingDetailDto> dtos);
}