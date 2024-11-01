package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.CommentDto;
import com.mewcare.meow_care_service.entities.Comment;
import com.mewcare.meow_care_service.mapper.CommentMapper;
import com.mewcare.meow_care_service.repositories.CommentRepository;
import com.mewcare.meow_care_service.services.CommentService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;

public class CommentServiceImpl extends BaseServiceImpl<CommentDto, Comment, CommentRepository, CommentMapper>
        implements CommentService {
    public CommentServiceImpl(CommentRepository repository, CommentMapper mapper) {
        super(repository, mapper);
    }
}
