package com.meow_care.meow_care_service.controller;

import com.meow_care.meow_care_service.dto.PostDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public ApiResponse<PostDto> getPostById(@PathVariable UUID id) {
        return postService.get(id);
    }

    @GetMapping
    public ApiResponse<List<PostDto>> getAllPosts() {
        return postService.getAll();
    }

    @PostMapping
    public ApiResponse<PostDto> createPost(@RequestBody PostDto postDto) {
        return postService.create(postDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<PostDto> updatePost(@PathVariable UUID id, @RequestBody PostDto postDto) {
        return postService.update(id, postDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePost(@PathVariable UUID id) {
        return postService.delete(id);
    }
}
