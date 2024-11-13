package com.meow_care.meow_care_service.services.base;

import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.BaseMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class BaseServiceImpl<D, E, R extends JpaRepository<E, UUID>, M extends BaseMapper<D, E>> implements BaseService<D, E> {

    protected final R repository;
    protected final M mapper;

    @Override
    public E findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
    }

    @Override
    public ApiResponse<D> findById(UUID id) {
        E entity = repository.findById(id)
                .orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        return ApiResponse.success(mapper.toDto(entity));
    }

    @Override
    public ApiResponse<D> create(D dto) {
        E entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        return ApiResponse.created(mapper.toDto(entity));
    }

    @Override
    public ApiResponse<List<D>> getAll() {
        List<E> entities = repository.findAll();
        return ApiResponse.success(mapper.toDtoList(entities));
    }

    @Override
    public ApiResponse<D> get(UUID id) {
        E entity = repository.findById(id)
                .orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        return ApiResponse.success(mapper.toDto(entity));
    }

    @Override
    public ApiResponse<D> update(UUID id, D dto) {
        E entity = repository.findById(id)
                .orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        mapper.partialUpdate(dto, entity);
        entity = repository.save(entity);
        return ApiResponse.updated(mapper.toDto(entity));
    }

    @Override
    public ApiResponse<Void> delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ApiException(ApiStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        return ApiResponse.deleted();
    }
}
