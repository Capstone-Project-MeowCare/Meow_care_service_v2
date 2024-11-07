package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.PostDto;
import com.meow_care.meow_care_service.entities.Post;
import com.meow_care.meow_care_service.mapper.PostMapper;
import com.meow_care.meow_care_service.repositories.PostRepository;
import com.meow_care.meow_care_service.services.PostService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl extends BaseServiceImpl<PostDto, Post, PostRepository, PostMapper>
        implements PostService {
    public PostServiceImpl(PostRepository repository, PostMapper mapper) {
        super(repository, mapper);
    }
}
