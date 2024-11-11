package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.care_schedule.CareScheduleDto;
import com.meow_care.meow_care_service.dto.care_schedule.CareScheduleWithTaskDto;
import com.meow_care.meow_care_service.entities.CareSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {PetProfileMapper.class, TaskMapper.class})
public interface CareScheduleMapper extends BaseMapper<CareScheduleDto, CareSchedule> {

    CareScheduleWithTaskDto toDtoWithTask(CareSchedule careSchedule);

    //list
    List<CareScheduleWithTaskDto> toDtoWithTask(List<CareSchedule> careSchedules);

}