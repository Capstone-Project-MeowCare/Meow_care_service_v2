package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.ReactionDto;
import com.meow_care.meow_care_service.entities.Reaction;
import com.meow_care.meow_care_service.mapper.ReactionMapper;
import com.meow_care.meow_care_service.repositories.ReactionRepository;
import com.meow_care.meow_care_service.services.ReactionService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ReactionServiceImpl extends BaseServiceImpl<ReactionDto, Reaction, ReactionRepository, ReactionMapper>
        implements ReactionService {
    public ReactionServiceImpl(ReactionRepository repository, ReactionMapper mapper) {
        super(repository, mapper);
    }
}
