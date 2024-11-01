package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.UserQuizResultDto;
import com.mewcare.meow_care_service.entities.UserQuizResult;
import com.mewcare.meow_care_service.mapper.UserQuizResultMapper;
import com.mewcare.meow_care_service.repositories.UserQuizResultRepository;
import com.mewcare.meow_care_service.services.UserQuizResultService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;

public class UserQuizResultServiceImpl extends BaseServiceImpl<UserQuizResultDto, UserQuizResult, UserQuizResultRepository, UserQuizResultMapper>
        implements UserQuizResultService {
    public UserQuizResultServiceImpl(UserQuizResultRepository repository, UserQuizResultMapper mapper) {
        super(repository, mapper);
    }
}
