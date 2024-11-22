package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.UserQuizResultDto;
import com.meow_care.meow_care_service.dto.UserQuizResultWithQuizDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.UserQuizResult;
import com.meow_care.meow_care_service.mapper.UserQuizResultMapper;
import com.meow_care.meow_care_service.repositories.UserQuizResultRepository;
import com.meow_care.meow_care_service.services.UserQuizResultService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.meow_care.meow_care_service.util.UserUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.UUID;

@Service
public class UserQuizResultServiceImpl extends BaseServiceImpl<UserQuizResultDto, UserQuizResult, UserQuizResultRepository, UserQuizResultMapper>
        implements UserQuizResultService {
    public UserQuizResultServiceImpl(UserQuizResultRepository repository, UserQuizResultMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ApiResponse<UserQuizResultDto> create(UserQuizResultDto userQuizResultDto) {
        UserQuizResult userQuizResult = mapper.toEntity(userQuizResultDto);
        userQuizResult.setUserId(UserUtils.getCurrentUserId());
        userQuizResult = repository.save(userQuizResult);
        return ApiResponse.success(mapper.toDto(userQuizResult));
    }

    @Override
    public ApiResponse<List<UserQuizResultWithQuizDto>> getByUserId(UUID userId) {
        List<UserQuizResult> userQuizResults = repository.findByUserId(userId);
        return ApiResponse.success(mapper.toDtoWithQuizList(userQuizResults));
    }

    //get by user id and month
    @Override
    public ApiResponse<List<UserQuizResultWithQuizDto>> getByUserIdAndMonth(UUID userId, int month, int year) {
        Instant firstDayOfMonth = LocalDate.of(year, month, 1).atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant lastDayOfMonth = LocalDate.of(year, month, 1).with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();
        List<UserQuizResult> userQuizResults = repository.findByUserIdAndCreatedAtGreaterThanAndCreatedAtLessThan(userId, firstDayOfMonth, lastDayOfMonth);
        return ApiResponse.success(mapper.toDtoWithQuizList(userQuizResults));
    }
}
