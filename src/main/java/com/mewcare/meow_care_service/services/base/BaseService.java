package com.mewcare.meow_care_service.services.base;

import com.mewcare.meow_care_service.dto.response.ApiResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BaseService<D, E> {
    Optional<E> findEntityById(UUID id);

    ApiResponse<D> findById(UUID id);

    ApiResponse<D> create(D dto);

    ApiResponse<List<D>> getAll();

    ApiResponse<D> get(UUID id);

    ApiResponse<D> update(UUID id, D dto);

    ApiResponse<Void> delete(UUID id);
}
