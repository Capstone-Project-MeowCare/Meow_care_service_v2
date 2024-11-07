package com.meow_care.meow_care_service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;

import java.util.List;

public interface BaseMapper<D, E> {
    D toDto(E entity);

    E toEntity(D dto);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(D dto, @MappingTarget E entity);

    List<D> toDtoList(List<E> entityList);
}
