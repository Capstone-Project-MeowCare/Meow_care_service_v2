package com.mewcare.meow_care_service.services.Impl;

import com.mewcare.meow_care_service.dto.PostDto;
import com.mewcare.meow_care_service.entities.Post;
import com.mewcare.meow_care_service.mapper.PostMapper;
import com.mewcare.meow_care_service.repositories.PostRepository;
import com.mewcare.meow_care_service.services.PostService;
import com.mewcare.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl extends BaseServiceImpl<PostDto, Post, PostRepository, PostMapper>
        implements PostService {
    public PostServiceImpl(PostRepository repository, PostMapper mapper) {
        super(repository, mapper);
    }
}
