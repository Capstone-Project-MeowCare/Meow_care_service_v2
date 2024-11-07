package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.CommentDto;
import com.meow_care.meow_care_service.entities.Comment;
import com.meow_care.meow_care_service.mapper.CommentMapper;
import com.meow_care.meow_care_service.repositories.CommentRepository;
import com.meow_care.meow_care_service.services.CommentService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends BaseServiceImpl<CommentDto, Comment, CommentRepository, CommentMapper>
        implements CommentService {
    public CommentServiceImpl(CommentRepository repository, CommentMapper mapper) {
        super(repository, mapper);
    }
}
