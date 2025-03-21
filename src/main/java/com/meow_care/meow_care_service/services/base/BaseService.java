package com.meow_care.meow_care_service.services.base;

import com.meow_care.meow_care_service.dto.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BaseService<D, E> {
    E findEntityById(UUID id);

    @SuppressWarnings("unused")
    ApiResponse<D> findById(UUID id);

    ApiResponse<D> create(D dto);

    ApiResponse<List<D>> getAll();

    ApiResponse<Page<D>> getAllPaginated(Pageable pageable);

    ApiResponse<D> get(UUID id);

    ApiResponse<D> update(UUID id, D dto);

    ApiResponse<Void> delete(UUID id);
}
