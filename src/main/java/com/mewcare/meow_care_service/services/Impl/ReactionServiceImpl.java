package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.ReactionDto;
import com.mewcare.meow_care_service.entities.Reaction;
import com.mewcare.meow_care_service.mapper.ReactionMapper;
import com.mewcare.meow_care_service.repositories.ReactionRepository;
import com.mewcare.meow_care_service.services.ReactionService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;

public class ReactionServiceImpl extends BaseServiceImpl<ReactionDto, Reaction, ReactionRepository, ReactionMapper>
        implements ReactionService {
    public ReactionServiceImpl(ReactionRepository repository, ReactionMapper mapper) {
        super(repository, mapper);
    }
}
