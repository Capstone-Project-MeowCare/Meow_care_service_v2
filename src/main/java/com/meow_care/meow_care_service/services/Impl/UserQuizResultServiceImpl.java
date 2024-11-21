package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.UserQuizResultDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.UserQuizResult;
import com.meow_care.meow_care_service.mapper.UserQuizResultMapper;
import com.meow_care.meow_care_service.repositories.UserQuizResultRepository;
import com.meow_care.meow_care_service.services.UserQuizResultService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserQuizResultServiceImpl extends BaseServiceImpl<UserQuizResultDto, UserQuizResult, UserQuizResultRepository, UserQuizResultMapper>
        implements UserQuizResultService {
    public UserQuizResultServiceImpl(UserQuizResultRepository repository, UserQuizResultMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ApiResponse<UserQuizResultDto> create(UUID userId, UserQuizResultDto dto) {
        UserQuizResult userQuizResult = mapper.toEntity(dto);
        userQuizResult.setUserId(userId);
        userQuizResult = repository.save(userQuizResult);
        return ApiResponse.success(mapper.toDto(userQuizResult));
    }
}
