package com.meow_care.meow_care_service.mapper;

import com.meow_care.meow_care_service.dto.task.TaskDto;
import com.meow_care.meow_care_service.dto.task.TaskWithPetProfileDto;
import com.meow_care.meow_care_service.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {PetProfileMapper.class})
public interface TaskMapper extends BaseMapper<TaskDto, Task> {
    @Mapping(target = "subTasks", source = "tasks")
    TaskWithPetProfileDto toDtoWithPetProfile(Task entity);

    @SuppressWarnings("unused")
    List<TaskWithPetProfileDto> toDtoWithPetProfile(List<Task> entities);
}